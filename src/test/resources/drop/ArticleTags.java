package drop;

public class ArticleTags {
    private int oId;
    private int tagId;
    private int articleId;
    private int articleTagStatus;

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getArticleTagStatus() {
        return articleTagStatus;
    }

    public void setArticleTagStatus(int articleTagStatus) {
        this.articleTagStatus = articleTagStatus;
    }
}
