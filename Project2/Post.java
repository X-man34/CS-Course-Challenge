

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;

public class Post implements PostInterface{


    private int postId;
    private String author;
    private String text;
    private Instant timestamp;
    private ArrayList<String> commentsList = new ArrayList<>();

    private final String fileSeperator = " ";
    public Post(int id, String author, String text) {
        this.author = author;
        this.text = text;
        this.postId = id;
        this.timestamp = Instant.now();
        this.save();
    }

    /**
     * attempts to create post object from saved file
     * if the file does not exist or some other io problem occurs, an IO exception is thrown
     * if the file exists but the first line is not properly formatter, dummy values are used and the program attempts to recover
     * comment data. 
     * 
     * @param id id of post
     * @throws IOException if unable to create post from file
     */
    public Post(int id) throws IOException {
        this.postId = id;
        //dummy values in case stuff goes wrong
        this.author = "Unknown Author";
        this.timestamp = Instant.now();
        this.text = "Original Post Unknown";
        File file = new File(getFilename());//have to set the post Id before calling this
        if (!file.exists()) {
            return;
        }
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line = in.readLine();
            String[] tokens = line.split(fileSeperator);
            if (tokens.length < 4) {
                in.close();
                //i'm debating between printing stuff out and throwing a exception and I think this is better becuase the user can at least potentially view properly formatted comments. 
                System.err.println("Invalid Post Decleration in file: " + file.getAbsolutePath() + ", will use dummy values");
            }else {
                this.timestamp = Instant.parse(tokens[1]);
                this.author = tokens[2];
                this.text = tokens[3];//indecis 0-3 are gaurenteed to exist, if the file seperator is contained in the text then there will be additional tokens, so just string them all back together. 
                for (int i = 4; i < tokens.length; i++) {
                    text += " " + tokens[i];
                }
            }


            while (line != null) {
                line = in.readLine();
                if (line == null) {
                    break;
                }
                commentsList.add(line);
            }
        
            in.close(); 


    }
    /**
     * save the currnet post file in the format specified (personnaly I would use JSON for this)
     */
    private void save() {
        File file = new File(getFilename());
        try {
            if (file.isFile()) {
                file.delete();
            }
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(getFormattedID() + fileSeperator + timestamp.toString() + fileSeperator + author + fileSeperator + text + "\n");

            for (String comment : commentsList) {
                writer.append(comment + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        
    }

    @Override
    public void addComment(String author, String text) {
        this.commentsList.add(String.valueOf(Instant.now()) + fileSeperator + author + fileSeperator + text);
        this.save();
    }

    @Override
    public String toStringPostOnly() {
        return getFormattedID() + " " +  timestamp.toString() + " " + author + " " + text;
    }

    @Override
    public String getFilename() {
        return "Post-" + getFormattedID() + ".txt";
    }

    @Override
    public int getPostID() {
        return this.postId;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String getAuthor() {
        return this.author;
    }

    @Override
    public String toString() {
        String str ="Post: \n" + 
            this.toStringPostOnly() + "\n" + 
            "Comments: \n";
        for (String comment : this.commentsList) {
            str += comment + "\n";
        }
        return str;

    }

    public String getFormattedID() {
        DecimalFormat format = new DecimalFormat("00000");
        return format.format(postId);
    }

}
