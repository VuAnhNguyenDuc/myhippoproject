package org.example.components;

import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.Nullable;
import org.apache.jackrabbit.util.Text;
import org.example.components.info.CustomDocumentListComponentInfo;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.FilterException;
import org.hippoecm.hst.content.beans.query.filter.BaseFilter;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.util.EncodingUtils;
import org.hippoecm.hst.util.SearchInputParsingUtils;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;

@ParametersInfo(type = CustomDocumentListComponentInfo.class)
public class CustomListComponent extends EssentialsListComponent {
    private static final String DEFAULT_IGNORED_CHARS = "+-'&|!(){}[]^\"~*?:\\°";
    private static final int WILD_CARDS_MIN_LENGTH = 3;
    private static final int SEARCH_TERM_MIN_LENGTH = 2;
    private static Logger log = LoggerFactory.getLogger(CustomListComponent.class);

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
    }

    /**
     * Apply search filter (query) to result list
     *
     * @param request current HST request
     * @param query   query under construction, provider for new filters
     * @throws FilterException
     */
    protected Filter createQueryFilter(final HstRequest request, final HstQuery query, String searchField, String queryParam) throws FilterException {
        Filter queryFilter = null;

        // check if we have query parameter

        if (!Strings.isNullOrEmpty(queryParam)) {
            log.debug("using search query {}", queryParam);

            queryFilter = query.createFilter();
            queryFilter.addEqualTo(searchField, queryParam);
        }
        return queryFilter;
    }

    protected Filter createQueryCompare(final HstRequest request, final HstQuery query, String searchField, Long queryParam) throws FilterException {
        Filter queryFilter = null;

        // check if we have query parameter
        log.debug("using search query {}", queryParam);

        queryFilter = query.createFilter();
        queryFilter.addGreaterOrEqualThan(searchField, queryParam);
        return queryFilter;
    }

    /**
     * cleaning invalid chars is handled by this component.
     */
    @Nullable
    @Override
    public String cleanupSearchQuery(final String input) {
        if (input == null) {
            return null;
        }
        String parsed = SearchInputParsingUtils.compressWhitespace(input);
        parsed = SearchInputParsingUtils.removeLeadingOrTrailingOrOperator(parsed);
        parsed = rewriteNotOperatorsToMinus(parsed);
        parsed = removeLeadingAndTrailingAndReplaceWithSpaceAndOperators(parsed);
        parsed = EncodingUtils.foldToASCIIReplacer(parsed);
        log.debug("Rewrote input '{}' to '{}'", input, parsed);
        return parsed;
    }

    /**
     *
     * @param queryParam
     * @return
     */
    private static String getJCRQueryParam(String queryParam) {
        if (StringUtils.isEmpty(queryParam)) return queryParam;
        String searchTerms = Text.escapeIllegalXpathSearchChars(getSearchKeys(queryParam));
        StringTokenizer st = new StringTokenizer(searchTerms, " ");
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.length() >= SEARCH_TERM_MIN_LENGTH) {
                sb.append(token).append(" ");
            }
        }

        if (sb.length() > 0) {
            String term = sb.substring(0, sb.length() - 1);

            if (term.length() >= WILD_CARDS_MIN_LENGTH) {
                return term + "*";
            }

            return term;
        }

        return null;
    }

    /**
     *
     * @param queryParam
     * @return
     */
    private static String getSearchKeys(String queryParam) {
        String ignoredChars = DEFAULT_IGNORED_CHARS;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < queryParam.length(); i++) {
            char c = queryParam.charAt(i);
            if (ignoredChars.indexOf(c) == -1) {
                if (c == ' ') {
                    if (sb.length() > 0 && sb.charAt(sb.length() - 1) != c) {
                        sb.append(c);
                    }
                } else {
                    sb.append(c);
                }
            } else if (sb.length() > 0 && sb.charAt(sb.length() -1) != ' ') {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    /*
     * @see ${SearchInputParsingUtils.rewriteNotOperatorsToMinus}
     * @param input keywords to rewrite
     * @return rewritten input
     */
    private static String rewriteNotOperatorsToMinus(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input is not allowed to be null");
        }
        return input.replace("NOT ", "-");
    }

    /*
     * @see ${SearchInputParsingUtils.removeLeadingAndTrailingAndReplaceWithSpaceAndOperators}
     * @param input keywords to rewrite
     * @return rewritten input
     */
    private static String removeLeadingAndTrailingAndReplaceWithSpaceAndOperators(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input is not allowed to be null");
        }
        String output = input;
        output = StringUtils.removeStart(output, "AND ");
        output = StringUtils.removeEnd(output, " AND");
        return output.replace(" AND ", " ");
    }

    @Override
    protected void buildAndApplyFilters(HstRequest request, HstQuery query) throws FilterException {
        final List<BaseFilter> filters = new ArrayList<>();
        final CustomDocumentListComponentInfo paramInfo = getComponentParametersInfo(request);
        final String keyword = paramInfo.getKeyword();
        final String sortField = paramInfo.getSortField();
        final String from = paramInfo.getFrom();
        final String to = paramInfo.getTo();
        final String lower = paramInfo.getLower();
        final String higher = paramInfo.getHigher();
        final Boolean isDate = paramInfo.isDate();

        SimpleDateFormat sdf = new SimpleDateFormat("M-dd-yyyy hh:mm");
        Calendar cal2 = Calendar.getInstance();
        Calendar cal1 = Calendar.getInstance();

        //final String title = request.getParameter("title");

        contributeAndFilters(filters,request,query);

        List<String> searchFields = new ArrayList<>();
        searchFields.add(sortField);
        final Filter queryFilter = query.createFilter();
        if(StringUtils.isNotBlank(keyword)){
            queryFilter.addAndFilter(createQueryFilter(request,query,sortField,keyword));
        }
        try {
            if(StringUtils.isNotBlank(from) && StringUtils.isNotBlank(to)){
                if(isDate){
                    cal1.setTime(sdf.parse(from));
                    cal2.setTime(sdf.parse(to));
                    queryFilter.addBetween(sortField,cal1,cal2);
                }else{
                    queryFilter.addBetween(sortField,from,to);
                }
            }
            if(StringUtils.isNotBlank(lower)){
                if(isDate){
                    cal1.setTime(sdf.parse(lower));
                    queryFilter.addLessThan(sortField,cal1);
                } else{
                    queryFilter.addLessThan(sortField,lower);
                }
            }
            if(StringUtils.isNotBlank(higher)){
                if(isDate){
                    cal2.setTime(sdf.parse(higher));
                    queryFilter.addGreaterThan(sortField,cal2);
                } else{
                    queryFilter.addGreaterThan(sortField,higher);
                }
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        //query.addOrderByAscending("qatartourism:title");
        filters.add(queryFilter);

        applyAndFilters(query, filters);
    }
}
