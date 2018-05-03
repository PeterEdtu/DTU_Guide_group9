package data;

public class Tag extends Searchable {

    private int id;
    private String tagText;

    public Tag(int id, String tagText) {
        this.id = id;
        this.tagText = tagText;
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

    @Override
    public String getSearchName() {
        return tagText;
    }
}
