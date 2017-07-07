package utils;

import java.util.List;

/**
 * Created by sl on 05.07.17.
 */
public class ResultSet {
    private String url;
    private List<String> links;
    private String html;

    public String getUrl() {
        return url;
    }

    public String makePath() {
        String[] s = url.split("//", 2);
        String res = s.length == 1 ? s[0] : s[1];
        if (!res.matches("^.*\\.html$"))
            res = res.concat(".html");
        return res;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public ResultSet(String url, List<String> links, String html) {
        this.url = url;
        this.links = links;
        this.html = html;
    }
}
