package boardPkg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class BoardDAO {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	String sql = null;

	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 전체리스트
	public Board[] selectBoards() {
		sql = "select * from board order by board_no desc";
		conn = DBcon.getConnection();
		Board[] boards = new Board[100];
		int i = 0;

		try { // conn = DBcon.getConnection(); >>> 예외처리
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) { // next=한건씩 읽어오겠다
				Board brd = new Board();
				brd.setBoardNo(rs.getInt("board_no"));
				brd.setTitle(rs.getString("title"));
				brd.setContent(rs.getString("content"));
				brd.setWriter(rs.getString("writer"));
				brd.setCreationDate(rs.getString("creation_date"));

				boards[i++] = brd;
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("조회처리중에 에러가 발생했습니다.");
		} finally { // 정상처리되건, 에러가 나건 항상 실행해야하는 내용이 있으면 구현,,,conn같은...
			close();
		}

		return boards;
	}

	// 한건 조회
	public Board selectBoard(int boardNo) {
		sql = "select * from board where board_no = " + boardNo;
		conn = DBcon.getConnection();
		Board brd = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				brd = new Board(rs.getInt("board_no"), rs.getString("title"), rs.getString("content"),
						rs.getString("writer"), rs.getString("creation_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return brd;
	}

	// 입력처리
	public void insertBoard(Board board) {
		conn = DBcon.getConnection();
		sql = "insert into board values(" + "" + board.getBoardNo() // 정수는 ''안써도 됨
				+ ", '" + board.getTitle() + "'" // 문자열이라서 ''넣어줘야함..
				+ ", '" + board.getContent() + "'" + ", '" + board.getWriter() + "'" + ", sysdate" + ")";
		try {
			stmt = conn.createStatement();
			int r = stmt.executeUpdate(sql); // update 사용해야 함
			System.out.println(r + "건 입력되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}
	
	//입력 후 결과 확인하게
	public Board insertBoardResult(Board board) {
		conn = DBcon.getConnection();
		sql = "insert into board values(" 
				+ "" + board.getBoardNo() // 정수는 ''안써도 됨
				+ ", '" + board.getTitle() + "'" // 문자열이라서 ''넣어줘야함..
				+ ", '" + board.getContent() + "'" + ""
				+ ", '" + board.getWriter() + "'" + ""
				+ ", sysdate" + ")";
		String sql1 = "select * from board where board_no = " + board.getBoardNo();
		Board returnVal = new Board();
		try {
			stmt = conn.createStatement();
			int r = stmt.executeUpdate(sql); // update 사용해야 함
			System.out.println(r + "건 입력되었습니다.");

			rs = stmt.executeQuery(sql1);
			if (rs.next()) {
				returnVal.setBoardNo(rs.getInt("board_no"));
				returnVal.setContent(rs.getString("content"));
				returnVal.setCreationDate(rs.getString("creation_date"));
				returnVal.setTitle(rs.getString("title"));
				returnVal.setWriter(rs.getString("writer"));
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return returnVal;
	}

	// 수정
	public void updateBoard(Board board) {
		conn = DBcon.getConnection();
		sql = "update board set content = '" + board.getContent() + "'" + "where board_no = " + board.getBoardNo();

		try {
			stmt = conn.createStatement();
			int r = stmt.executeUpdate(sql);
			System.out.println(r + "건이 업데이트 되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}

	// 삭제
	public Board deleteBoard(Board board) {
		conn = DBcon.getConnection();
		sql = "delete from board where board_no = " + board.getBoardNo();
		try {
			stmt = conn.createStatement();
			int r = stmt.executeUpdate(sql);
			System.out.println(r + "건이 삭제 되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return board;
	}
	
	
	
	// 전체리스트
		public List<Board> getBoardList() {
			sql = "select * from board order by board_no";
			conn = DBcon.getConnection();
			List<Board> boards = new ArrayList<>();
			int i = 0;

			try { // conn = DBcon.getConnection(); >>> 예외처리
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) { // next=한건씩 읽어오겠다
					Board brd = new Board();
					brd.setBoardNo(rs.getInt("board_no"));
					brd.setTitle(rs.getString("title"));
					brd.setContent(rs.getString("content"));
					brd.setWriter(rs.getString("writer"));
					brd.setCreationDate(rs.getString("creation_date"));

					boards.add(brd);
				}
			} catch (SQLException e) {
				// e.printStackTrace();
				System.out.println("조회처리중에 에러가 발생했습니다.");
			} finally { // 정상처리되건, 에러가 나건 항상 실행해야하는 내용이 있으면 구현,,,conn같은...
				close();
			}
			return boards;
		}
	

}//end of class






