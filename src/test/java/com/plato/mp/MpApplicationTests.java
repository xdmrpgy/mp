package com.plato.mp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.plato.mp.entity.User;
import com.plato.mp.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MpApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void select() {
		List<User> list = userMapper.selectList(null);
		Assert.assertEquals(3,list.size());
		list.forEach(System.out::println);
	}

	@Test
	public void insert() {
		User user = new User();
		user.setUserId(4L);
		user.setUserName("罗素");
		user.setPassword("234234");
		user.setPhone("123123");
		int rows = userMapper.insert(user);
		System.out.println("影响记录数：" + rows);
	}

	@Test
	public void selectById() {
		User user = userMapper.selectById(1L);
		System.out.println(user);
	}

	@Test
	public void selectByMap() {
		HashMap<String, Object> columnMap = new HashMap<>();
		//key 存的为数据库中的字段名
		columnMap.put("userId",1L);
		List<User> users = userMapper.selectByMap(columnMap);
		users.forEach(System.out::println);
	}

	@Test
	public void selectByWrapper() {
		//查询userId > 2,且password 包含22的用户
//		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//		queryWrapper.gt("userId",1L).like("password","22");
//		List<User> users = userMapper.selectList(queryWrapper);
//		users.forEach(System.out::println);
		//查询userId > 2,或password 包含22的用户 ，按userId降序
		QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.gt("userId",2L).or().like("password","22").orderByDesc("userId");
		List<User> users2 = userMapper.selectList(queryWrapper2);
		users2.forEach(System.out::println);
	}

	@Test
	public void selectPartFields() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		//不返回全部字段，只返回userId,userName
		//queryWrapper.gt("userId",1L).like("password","22").select("userId","userName");
		//不返回全部字段，除了userId,userName的字段都返回
		queryWrapper.gt("userId",1L).like("password","22").select(User.class,info->!info.getColumn().equals("userId")&&
			!info.getColumn().equals("userName"));
		List<User> users = userMapper.selectList(queryWrapper);
		users.forEach(System.out::println);
	}


	@Test
	public void testCondition() {
		String userName = "柏拉图";
		String password = "";
		condition(userName,password);
	}

	public void condition(String userName,String password) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//		if (!StringUtils.isEmpty(userName)) {
//			queryWrapper.like("userName",userName);
//		}
//		if (!StringUtils.isEmpty(password)) {
//			queryWrapper.like("password",password);
//		}
		queryWrapper.like(!StringUtils.isEmpty(userName),"userName",userName)
				.like(!StringUtils.isEmpty(password),"password",password);
		List<User> users = userMapper.selectList(queryWrapper);
		users.forEach(System.out::println);
	}

	@Test
	public void selectByWrapperEntity() {
		User whereUser = new User();
		whereUser.setUserName("柏拉图");
		whereUser.setUserId(2L);
		QueryWrapper<User> q = new QueryWrapper<>(whereUser);
		List<User> users = userMapper.selectList(q);
		users.forEach(System.out::println);
	}

	@Test
	public void selectByWrapperAllEq() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		Map<String,Object> params = new HashMap<>();
		params.put("userName","柏拉图");
		params.put("password",null);
		queryWrapper.allEq(params,false);
		List<User> users = userMapper.selectList(queryWrapper);
		users.forEach(System.out::println);
	}

	@Test
	public void selectByWrapperMaps() {
		QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.gt("userId",2L).or().like("password","22").orderByDesc("userId")
				.select("userId","userName");
		//返回map，不返回User对象
		List<Map<String,Object>> maps = userMapper.selectMaps(queryWrapper2);
		maps.forEach(System.out::println);
	}

	@Test
	public void selectByWrapperOne() {
		QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.gt("userId",2L).or().like("password","22").orderByDesc("userId");
		//返回map，不返回User对象
		User user = userMapper.selectOne(queryWrapper2);
		System.out.println(user);
	}

	@Test
	public void selectByWrapperLambda() {
//		LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
//		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		LambdaQueryWrapper<User> lambdaQuery = Wrappers.<User>lambdaQuery();
		lambdaQuery.gt(User::getUserId,2L).or().like(User::getPassword,"22");
		//返回map，不返回User对象
		List<User> users = userMapper.selectList(lambdaQuery);
		users.forEach(System.out::println);
	}
}
