package com.ivm.CustomerDetect;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

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
}
