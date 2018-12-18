package drop;

public class Tags {
    private int oId;
    private String tagName;
    private int status;
    private String tagDescription;
    private int tagReferenceCount;

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

    public int getTagReferenceCount() {
        return tagReferenceCount;
    }

    public void setTagReferenceCount(int tagReferenceCount) {
        this.tagReferenceCount = tagReferenceCount;
    }
}
