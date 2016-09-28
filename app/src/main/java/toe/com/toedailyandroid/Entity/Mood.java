package toe.com.toedailyandroid.Entity;

/**
 * Created by HQu on 9/28/2016.
 */

public class Mood {
    private String moodType;
    private String moodContent;

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
}
