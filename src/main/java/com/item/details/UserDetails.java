package com.item.details;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mkyong.User;
import com.mkyong.UsersList;

@Path("/register")
public class UserDetails {

	public static List<User> usersList = new ArrayList<User>();
	public static long userId = 1000;
	public static UsersList usersListObject = new UsersList();

	@POST
	@Path("/user/info")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User addUser(User user) throws Exception {
		boolean userFlag = true;
		User user2 = new User();
		if (user != null) {
			String userName = user.getName().trim();
			String phoneNumber = user.getPhoneNumber().trim();
			String email = user.getEmail().trim();

			user2.setName(userName);
			user2.setEmail(email);
			user2.setPhoneNumber(phoneNumber);
			user2.setAddress(user.getAddress());
			if (usersList != null && usersList.size() > 0) {
				for (User user1 : usersList) {
					if (!((userName.equals(user1.getName())) && (phoneNumber
							.equals(user1.getPhoneNumber()) || email
							.equals(user1.getEmail())))) {
						userId = userId + 1;
						user2.setUserId(userId);
						userFlag = false;
						usersList.add(user2);
						usersListObject.setUsers(usersList);
						System.out.println("usersList:::: " + usersList);
						return user2;
					}
					System.out
							.println("for loop outside block:::: " + userFlag);
				}

			} else {
				userId = userId + 1;
				user2.setUserId(userId);
				userFlag = false;
				usersList.add(user2);
				usersListObject.setUsers(usersList);
				System.out.println("usersList:::: " + usersList);
				return user2;
			}
		}

		if (userFlag) {
			throw new Exception("USERS ALREADY EXISTS");
		}
		System.out.println("main return:::: " + userFlag);

		return user2;
	}

	@GET
	@Path("/users/list")
	@Produces(MediaType.APPLICATION_JSON)
	public UsersList getUserDetails() {

		List<User> usersList = usersListObject.getUsers();
		System.out.println(usersList.size());
		return usersListObject;

	}

}
