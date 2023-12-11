import functions.GetRequests;
import hooks.Hooks;
import hooks.UtilsRest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;

@Test
public class TestsRequest extends GetRequests{
    @Test
    public void getAllPosts () throws IOException {
        getMethod(UtilsRest.getUrl() + "posts" , 200);
    }

    @Test
    public void getPostById () throws IOException {
        getMethod(UtilsRest.getUrl() + "posts/7", 200);
    }

    @Test
    public void getCommentsFromPostId () throws IOException {
        getMethod(UtilsRest.getUrl() + "posts/comments?postId=4", 404);
    }

    @Test
    public void getCommentsById() throws IOException {
        getMethod(UtilsRest.getUrl() + "posts/5/comments" , 200);
    }

    @Test
    public void postNewPost () throws IOException {
        postMethod(UtilsRest.getUrl() + "posts", UtilsRest.readPost(), 201);
    }
}
