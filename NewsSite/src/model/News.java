package NewsSite.src.model;

public class News {
    private int id;
    private String title;
    private String category;
    private String content;
    private String publishTime;

    public News(int id, String title, String category, String content, String publishTime) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.publishTime = publishTime;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getContent() { return content; }
    public String getPublishTime() { return publishTime; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setCategory(String category) { this.category = category; }
    public void setContent(String content) { this.content = content; }
    public void setPublishTime(String publishTime) { this.publishTime = publishTime; }
}