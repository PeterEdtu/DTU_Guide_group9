package data;

public class Tag extends Searchable {

    private int id;
    private String tagText;

    public Tag() {

    }

    public Tag(int id, String tagText) {
        this.id = id;
        this.tagText = tagText;
    }

    public Tag(Tag tag) {
        this(tag.id,
                tag.tagText);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tagText='" + tagText + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagText() {
        return tagText;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }
}
