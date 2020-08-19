package com.ivm.CustomerDetect;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import com.ivm.CustomerDetect.model.StayRecordModel;
import com.ivm.CustomerDetect.model.UserInfoModel;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerDetectApplicationTests
{
	@Autowired
	private TestRestTemplate template;

	@Test
	void test_get_UserInfoModelByUid()
	{
		final ResponseEntity<UserInfoModel> model = template.getForEntity("/visitor/uid/{uid}", UserInfoModel.class, 3);
		Assert.isTrue(model.hasBody(), "The GET /user/uid/3 methods returns nothing");
		Assert.notNull(model.getBody(), "Null object");
		Assert.isTrue(model.getBody().getUid()==3, "Incorrect user id");
		System.out.println(model.getBody());
	}

	@Test
	void test_get_UserInfoModelByName()
	{
		final ResponseEntity<UserInfoModel[]> model = template.getForEntity("/visitor/name/{uName}", UserInfoModel[].class, "David");
		Assert.isTrue(model.hasBody(), "The GET /user/uid/David methods returns nothing");
		Assert.notNull(model.getBody(), "Null object");
		for(UserInfoModel eachModel : model.getBody())
		{
			Assert.notNull(eachModel, "Null item in the returned object");
			Assert.isTrue(eachModel.getName().equals("David"), "Incorrect user name");
			System.out.println(eachModel);
		}
	}
	
	@Test
	void test_get_AllUserInfoModel()
	{
		final ResponseEntity<UserInfoModel[]> model = template.getForEntity("/visitor/lists", UserInfoModel[].class);
		Assert.isTrue(model.hasBody(), "The GET /user/lists methods returns nothing");
		Assert.notNull(model.getBody(), "Null object");
		for(UserInfoModel eachModel : model.getBody())
		{
			Assert.notNull(eachModel, "Null item in the returned object");
			System.out.println(eachModel);
		}
	}

	@Test
	void test_put_faceimageEntry()
	{
		template.put("/visitor/image/{uid}?imgPath=0F0F0F0F&facePath=F0F0F0F0", null, 1);
		final ResponseEntity<UserInfoModel> model = template.getForEntity("/visitor/uid/{uid}", UserInfoModel.class, 1);
		Assert.isTrue(model.hasBody(), "The GET /user/uid/1 methods returns nothing");
		Assert.notNull(model.getBody(), "Null object");
		System.out.println(model.getBody());
	}

	@Test
	void insert_new_stay()
	{
		StayRecordModel newStay = new StayRecordModel();
		newStay.setDatetimeIn("2020-8-19 8:51:00");
		newStay.setUid("-1");
		template.put("/visitor/stay?name=F2950A5E5D365C", newStay);
		final ResponseEntity<UserInfoModel []> model = template.getForEntity("/visitor/name/{name}", UserInfoModel[].class, "F2950A5E5D365C");
		Assert.isTrue(model.hasBody(), "The get method fails");
		Assert.notNull(model.getBody(), "Non such entry for this new user F2950A5E5D365C");
		Assert.notEmpty(model.getBody(), "Empty entry set for this new user F2950A5E5D365C");
		UserInfoModel newUser = model.getBody()[model.getBody().length-1];
		Assert.isTrue(newUser.getName().equals("F2950A5E5D365C"), "Wrong user name");
		Assert.notEmpty(newUser.getRecords(), "No records for what was just inserted");
		Assert.isTrue(newUser.getImgPath().length == 0, "Unexpected image path for this user");
		for(StayRecordModel record : newUser.getRecords())
		{
			System.out.println(record);
		}
	}

	@Test
	void insert_new_stay2()
	{
		StayRecordModel newStay = new StayRecordModel();
		newStay.setDatetimeIn("2020-8-19 8:51:00");
		newStay.setUid("0");
		template.put("/visitor/stay?name=6EAC6268A10F", newStay);
		final ResponseEntity<UserInfoModel []> model = template.getForEntity("/visitor/name/{name}", UserInfoModel[].class, "6EAC6268A10F");
		Assert.isTrue(model.hasBody(), "The get method fails");
		Assert.notNull(model.getBody(), "Non such entry for this new user 6EAC6268A10F");
		Assert.notEmpty(model.getBody(), "Empty entry set for this new user 6EAC6268A10F");
		UserInfoModel newUser = model.getBody()[model.getBody().length-1];
		Assert.isTrue(newUser.getName().equals("6EAC6268A10F"), "Wrong user name");
		Assert.notEmpty(newUser.getRecords(), "No records for what was just inserted");
		Assert.notEmpty(newUser.getImgPath(), "No image path for this user");
		for(StayRecordModel record : newUser.getRecords())
		{
			System.out.println(record);
		}
		for(String path : newUser.getImgPath())
		{
			System.out.println(path);
		}
	}
}
