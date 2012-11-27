package tktl.gstudies.responseobjs;

/**
 * A class to map a SQL-query in controller received from browser.
 *
 * @author hkeijone
 */
public class Query {

    private String queryString;

    public Query(String queryString) {
        this.queryString = queryString;
    }

    public Query() {
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
