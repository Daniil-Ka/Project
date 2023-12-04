package org.basicprogramming;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.base.Country;
import com.vk.api.sdk.objects.groups.UserXtrRole;
import com.vk.api.sdk.objects.users.Fields;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.basicprogramming.db.DB;
import org.basicprogramming.db.models.Student;
import org.basicprogramming.db.models.VKUser;
import org.hibernate.Session;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LoadWebPage extends Application{
    public static final String APP_ID = "51802885";
    public static final String TOKEN_NAME = "access_token";
    public static final String PRIVATE_KEY = "P1WIsX2C8iak4xXG0Fnf";
    public static final String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    public static final String VK_AUTH_URL = "https://oauth.vk.com/authorize?client_id=" +
            APP_ID +
            "&response_type=token";
    public static String token = "";

    public static void main(String[] args) throws ClientException, ApiException {
        token = LoadWebPage.getToken();

        VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());
        ServiceActor actor = new ServiceActor(0, PRIVATE_KEY, token);

        var fields = new Fields[] {
                Fields.BDATE,
                Fields.NICKNAME,
                Fields.CITY,
                Fields.COUNTRY,
                Fields.EDUCATION,
                Fields.HOME_TOWN,
                Fields.SEX,
                Fields.PHOTO_BIG,
        };

        List<UserXtrRole> members = vk.groups().getMembersWithFields(actor, fields)
                .groupId("basicprogrammingrtf2023")
                .execute()
                .getItems();

        for (var member : members) {
            var vkUser = getVkUser(member);
            try (Session session = DB.openSession()) {
                session.beginTransaction();

                var student = findStudentByName(vkUser.getFirstName(), vkUser.getLastName(), session);
                vkUser.setStudent(student);

                session.persist(vkUser);
                session.getTransaction().commit();
            }
        }
    }

    private static Student findStudentByName(String firstName, String lastName, Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);

        criteriaQuery.select(root)
                .where(
                        criteriaBuilder.equal(root.get("firstName"), firstName),
                        criteriaBuilder.equal(root.get("lastName"), lastName)
                );

        TypedQuery<Student> query = session.createQuery(criteriaQuery);
        List<Student> students = query.getResultList();

        if (students.size() != 1) {
            return null;
        }
        return students.get(0);
    }

    private static VKUser getVkUser(UserXtrRole member) {
        var vkUser = new VKUser();
        vkUser.setFirstName(member.getFirstName());
        vkUser.setLastName(member.getLastName());
        vkUser.setClosed(member.getIsClosed());
        vkUser.setSex(member.getSex().name());
        vkUser.setPhoto(String.valueOf(member.getPhotoBig()));
        vkUser.setNickname(member.getNickname());
        vkUser.setCountry(Optional.ofNullable(member.getCountry()).map(Country::getTitle).orElse(null));

        var education = member.getFacultyName();
        if (education != null && !education.isEmpty()) {
            vkUser.setEducation(member.getFacultyName());
        }
        else {
            vkUser.setEducation(member.getEducationForm());
        }
        return vkUser;
    }

    public static String getToken(){
        launch(LoadWebPage.class);
        return token;
    }

    public static Map<String, List<String>> getQueryParams(String url) {
        Map<String, List<String>> params = new HashMap<>();
        String[] urlParts = url.split("#");
        if (urlParts.length > 1) {
            String query = urlParts[1];
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                String key = URLDecoder.decode(pair[0], StandardCharsets.UTF_8);
                String value = "";
                if (pair.length > 1) {
                    value = URLDecoder.decode(pair[1], StandardCharsets.UTF_8);
                }

                List<String> values = params.computeIfAbsent(key, k -> new ArrayList<>());
                values.add(value);
            }
        }

        return params;
    }

    @Override
    public void start(Stage primaryStage) {
        final WebView view = new WebView();
        final WebEngine engine = view.getEngine();
        engine.load(VK_AUTH_URL);

        primaryStage.setScene(new Scene(view));
        primaryStage.show();

        engine.locationProperty().addListener((observableValue, oldUrl, newUrl) -> {
            if(newUrl.startsWith(REDIRECT_URL)){
                var params = getQueryParams(newUrl);
                token = params.get(TOKEN_NAME).get(0);
                primaryStage.close();
            }
        });
    }

}