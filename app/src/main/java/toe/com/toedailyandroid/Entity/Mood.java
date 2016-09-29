package toe.com.toedailyandroid.Entity;

/**
 * Created by HQu on 9/28/2016.
 */

public class Mood {
    private String moodType;
    private String moodContent;
    private String createdBy;
    private String createdAt;

    public Mood() {

    }
    public String getMoodType() {
        return moodType;
    }

    public void setMoodType(String moodType) {
        this.moodType = moodType;
    }

    public String getMoodContent() {
        return moodContent;
    }

    public void setMoodContent(String moodContent) {
        this.moodContent = moodContent;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
