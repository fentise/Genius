package drop;

public class Topic {
    private int oId;
    private String topicName;
    private int topicStatic;
    private String topicDescription;

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getTopicStatic() {
        return topicStatic;
    }

    public void setTopicStatic(int topicStatic) {
        this.topicStatic = topicStatic;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }
}
