package com.trello;

import com.gittigidiyor.common.TestUtility;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class ApiTest {

    static TestUtility utility = new TestUtility();

    private static String main_url = utility.readConfigProperties("config-qa.properties", "main_url");
    private static String trelloKey = utility.readConfigProperties("config-qa.properties", "trelloKey");
    private static String trelloToken = utility.readConfigProperties("config-qa.properties", "trelloToken");
    private static String idList = utility.readConfigProperties("config-qa.properties", "idList");
    private static String boardName = "This is temporary board";
    private static String boardID;
    private static String cardName = "I'm adding cards";
    private static String newCardName = "I'm updating this card";

    ArrayList<String> cardID_list = new ArrayList<>();

    @Test
    //Trello üzerinde bir board oluşturunuz.
    public void Test01_createBoard() {

        RestAssured.baseURI = main_url;

        JSONObject requestParam = new JSONObject();
        requestParam.put("key", trelloKey);
        requestParam.put("token", trelloToken);
        requestParam.put("name", boardName);

        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(requestParam.toString());

        Response response = httpRequest.post("/1/boards/");
        boardID = response.jsonPath().getString("id");

        String boardNameFromResponse = response.jsonPath().getString("name");
        Assert.assertEquals(boardName, boardNameFromResponse);
    }


    @Test
    //Oluşturduğunuz board’a iki tane kart oluşturunuz.
    public void Test02_createCards() {

        Response response = null;

        for (int i = 0; i < 2; i++) {

            RestAssured.baseURI = main_url;

            JSONObject requestParam = new JSONObject();
            requestParam.put("key", trelloKey);
            requestParam.put("token", trelloToken);
            requestParam.put("name", cardName);
            requestParam.put("idList", idList);

            RequestSpecification httpRequest = RestAssured.given();
            httpRequest.header("Content-Type", "application/json");
            httpRequest.body(requestParam.toString());
            response = httpRequest.post("/1/cards");
            String cardID = response.jsonPath().getString("id");
            cardID_list.add(cardID);
        }

        String cardNameFromResponse = response.jsonPath().getString("name");
        Assert.assertTrue(cardName.equals(cardNameFromResponse));
    }

    @Test
    //Oluştrduğunuz bu iki karttan random olacak sekilde bir tanesini güncelleyiniz.
    public void Test03_updateCard() {

        RestAssured.baseURI = main_url;

        JSONObject requestParam = new JSONObject();
        requestParam.put("key", trelloKey);
        requestParam.put("token", trelloToken);
        requestParam.put("name", newCardName);

        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(requestParam.toString());
        System.out.println("this is card ID: " + cardID_list.get(0));
        Response response = httpRequest.put("/1/cards/" + cardID_list.get(0));

        String newCardNameFromResponse = response.jsonPath().getString("name");
        Assert.assertTrue(!newCardNameFromResponse.equals(cardName));
    }

    @Test
    //Oluşturduğunuz kartları siliniz.
    public void Test04_deleteCards() {

        for (int i = 0; i < 2; i++) {

            RestAssured.baseURI = main_url;

            JSONObject requestParam = new JSONObject();
            requestParam.put("key", trelloKey);
            requestParam.put("token", trelloToken);

            RequestSpecification httpRequest = RestAssured.given();
            httpRequest.header("Content-Type", "application/json");
            httpRequest.body(requestParam.toString());
            System.out.println("this is card ID: " + cardID_list.get(i));
            Response response = httpRequest.delete("/1/cards/" + cardID_list.get(i));

            int statusCode = response.getStatusCode();
            Assert.assertTrue(statusCode == 200);
        }
    }

    @Test
    //Oluşturduğunuz board’u siliniz.
    public void Test05_deleteBoard() {

        RestAssured.baseURI = main_url;

        JSONObject requestParam = new JSONObject();
        requestParam.put("key", trelloKey);
        requestParam.put("token", trelloToken);

        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(requestParam.toString());
        System.out.println("this is card ID: " + boardID);
        Response response = httpRequest.delete("/1/boards/" + boardID);

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 200);
    }

}
