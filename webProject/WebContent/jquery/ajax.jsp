<%@page import="boardPkg.BoardDAO"%>
<%@page import="boardPkg.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<%
	String bno = request.getParameter("boardNo");
	String til = request.getParameter("title");
	String cnt = request.getParameter("content");
	String wrt = request.getParameter("writer");
	
	int boardNo = Integer.parseInt(bno);
	
	Board board = new Board();
	BoardDAO dao = new BoardDAO(); 
	board.setBoardNo(boardNo);
	board.setTitle(til);
	board.setContent(cnt);
	board.setWriter(wrt);
	
	dao.insertBoard(board);
	
	
	//{"name" : "Hong", " age" : 10, ...} => json타입
	String result = 
			"{\"boardNo\":\""+bno+"\","
			+ "\"title\":\""+til+"\","
			+ "\"content\":\""+cnt+"\","
			+ "\"writer\":\""+wrt+"\"}";
			
	out.print(result);
//{"boardNo":"1", "title":"test", "content":"test", "writer":"user1"}
%>