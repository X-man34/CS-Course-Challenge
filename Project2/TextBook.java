import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TextBook implements TextBookInterface{

    private ArrayList<Post> postsList = new ArrayList<>();
    private int lastID ;
    public TextBook() {
        this.lastID = 0;
        try {
            File postListFile = new File(TextBookInterface.POST_LIST_FILENAME);
            if (!postListFile.isFile()) {
                postListFile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(TextBookInterface.POST_LIST_FILENAME));
            String line = "";
            while (line != null) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                try {
                    postsList.add(new Post(Integer.parseInt(line)));
                    this.lastID  = Integer.parseInt(line);
                }catch (IOException e) {
                    System.err.println("Faild to initalize post with id/descriptor: " + line);
                }


            }
            reader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public int getLastID() {
        return lastID;
    }

    @Override
    public int getPostCount() {
        return postsList.size();
    }

    @Override
    public String getPostString(int index) {
        return postsList.get(index).toString();
    }

    @Override
    public void addPost(String author, String text) {
        lastID += 1;
        this.postsList.add(new Post(lastID, author, text));
        writePostListFile();
    }

    @Override
    public Post removePost(int index) {
        Post postToRemove = this.postsList.get(index);
        this.postsList.remove(index);
        writePostListFile();
        return postToRemove;
    }

    @Override
    public void addComment(int postIndex, String author, String text) {
        this.postsList.get(postIndex).addComment(author, text);
    }

    @Override
    public ArrayList<Post> getPosts() {
        return new ArrayList<>(postsList);
    }

    @Override
    public String toString() {
        String strToReturn = "TextBook contains " + String.valueOf(this.postsList.size()) + "  posts:";
        for (int i = 0; i < postsList.size(); i++) {
            strToReturn += "\n" + String.valueOf(i) + " - " + postsList.get(i).toStringPostOnly();
        }
        return strToReturn;
    }


    private void writePostListFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TextBookInterface.POST_LIST_FILENAME))) {
            for (Post post : postsList) {
                writer.write(String.valueOf(post.getPostID()) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
