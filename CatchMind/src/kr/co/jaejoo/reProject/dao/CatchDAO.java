package kr.co.jaejoo.reProject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.jaejoo.reProject.vo.CatchVO;

public class CatchDAO {
	// 
	
	private ResultSet rs = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = new StringBuffer();

	// singleton으로 자원을 연결합니다.
	public CatchDAO() {
		conn = CatchMindStingleton.getInstance().getConnection();
	}

	public ArrayList<CatchVO> selectAll() {
		ArrayList<CatchVO> list = new ArrayList<CatchVO>();
		sb.setLength(0);
		sb.append("SELECT * FROM CATCHMIND");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.executeQuery();

			while (rs.next()) {
				int userno = rs.getInt("userno");
				String name = rs.getString("name");
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String pw2 = rs.getString("pw2");
				int lev = rs.getInt("lev");
				int score = rs.getInt("score");
				CatchVO vo = new CatchVO(userno, name, id, pw, pw2, lev, score);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	public void insetjoin(CatchVO vo) {
		sb.setLength(0);
		sb.append("insert into catchmind ");
		sb.append("values (?, ?, ?, ?, 1, 0)");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getId());
			pstmt.setString(3, vo.getPw());
			pstmt.setString(4, vo.getPw2());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public CatchVO login(String id, String pw) {
		CatchVO vo = null;
		sb.setLength(0);
		sb.append("SELECT ID, PW FROM CATCHMIND ");
		sb.append("WHERE ID = ? AND PW = ?");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int userno = rs.getInt("userno");
				String name = rs.getString("name");
				String pw2 = rs.getString("pw2");
				int lev = rs.getInt("lev");
				int score = rs.getInt("score");

				vo = new CatchVO(userno, name, id, pw, pw2, lev, score);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (vo == null)System.out.println("값이 없습니다.");
			return vo;
	}

	public CatchVO selectOne(String id) {
		CatchVO vo = null;
		sb.setLength(0);
		sb.append("SELECT ID, SCORE, LEV FROM CATCHMIND ");
		sb.append("WHERE ID = ? ");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int userno = rs.getInt("userno");
				String name = rs.getString("name");
				String pw = rs.getString("pw");
				String pw2 = rs.getString("pw2");
				int lev = rs.getInt("lev");
				int score = rs.getInt("score");

				vo = new CatchVO(userno, name, id, pw, pw2, lev, score);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (vo == null) System.out.println("값이 정확하지 않음");
		return vo;
	}
	
	public CatchVO selectcheck(String id) {
		sb.setLength(0);
		sb.append("SELECT id FROM catchmind ");
		sb.append("WHERE id = ?");
		CatchVO vo = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int userno = rs.getInt("userno");
				String name = rs.getString("name");
				String pw = rs.getString("pw");
				String pw2 = rs.getString("pw2");
				int lev = rs.getInt("lev");
				int score = rs.getInt("score");

				vo = new CatchVO(userno, name, id, pw, pw2, lev, score);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (vo == null) System.out.println("값이 정확하지 않음");
		return vo;
	}

	public void Scoretwo(CatchVO vo){
		sb.setLength(0);
		sb.append("update catchmind ");
		sb.append("set score = ? ");
		sb.append("where id = ?");
		System.out.println(sb.toString());
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, vo.getScore());
			pstmt.setString(2, vo.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(CatchVO vo){
		sb.setLength(0);
		sb.append("update catchmind ");
		sb.append("set score = ?, lev = ? ");
		sb.append("where id = ? ");
		System.out.println("update : " + sb.toString());
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, vo.getScore());
			pstmt.setInt(2, vo.getLev());
			pstmt.setString(3, vo.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			if(rs != null)rs.close();
			if(conn != null)conn.close();
			if(pstmt != null)pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 
	
}
