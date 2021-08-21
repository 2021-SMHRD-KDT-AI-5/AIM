<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<%@page import="server_and_VO.BabyInfoDAO"%>
	<%@page import="server_and_VO.AIM_Member_DAO"%>

	<%
	BabyInfoDAO babyDao = new BabyInfoDAO();
	AIM_Member_DAO memberDao = new AIM_Member_DAO();
	
	/* 한글 깨짐 방지 */
	request.setCharacterEncoding("UTF-8");

	String task = request.getParameter("task"); // 수행작업 구분하는 변수
	String returns = null;
	
	if(task.equals("getBirthday")){ // 수행작업이 아기의 생일을 받아오는것이라면
		String id = request.getParameter("id");
	
		returns = babyDao.select_baby(id).get(0).getBirthday();
		
	}else if(task.equals("joinMember")){ // 회원가입
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		String ip = request.getParameter("ip");
		
		returns = memberDao.join(id, pw, email, ip);
		
	}else if(task.equals("joinBaby")){ // 아기 등록
		String id = request.getParameter("id");
		String babyName = request.getParameter("babyName");
		String babyBirthday = request.getParameter("babyBirthday");
		
		returns = Integer.toString(babyDao.join(id, babyName, babyBirthday));
		
	}else if(task.equals("getParentProfilePic")){ // 부모 프로필사진 불러오기
		String id = request.getParameter("id");
		
		returns = memberDao.select_member(id).get(0).getProfile_pic();
		
	}else if(task.equals("getBabyProfilePic")){ // 아기 프로필사진 불러오기
		String id = request.getParameter("id");
		
		returns = babyDao.select_baby(id).get(0).getProfile_pic();
		
	}else if(task.equals("setParentProfilePic")){ // 부모 프로필사진 저장
		String id = request.getParameter("id");
		String profilePic = request.getParameter("profilePic");
		
		returns = Integer.toString(memberDao.update_profile_pic(id, profilePic));
		
	}else if(task.equals("setBabyProfilePic")){ // 아기 프로필사진 저장
		String id = request.getParameter("id");
		String profilePic = request.getParameter("profilePic");
		
		returns = Integer.toString(babyDao.update_profile_pic(id, profilePic));
		
	}
	

	System.out.println(returns);

	// 안드로이드로 전송
	out.println(returns);
	%>

</body>
</html>