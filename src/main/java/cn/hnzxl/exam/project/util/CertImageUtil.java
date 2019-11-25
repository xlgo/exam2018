package cn.hnzxl.exam.project.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cn.hnzxl.exam.system.model.User;

public class CertImageUtil {
	public static void genCertImage(User user,Integer score,String basePath) throws IOException{
		String zsbh=(1382775151-user.getUserid()*462)+"";//证书编号
		String xxAndXm = String.format("%s        %s  同学：", user.getSchool(),user.getName());
		//String xxAndXm="河南政法大学经济管理学院        张三丰  同学：";//学校与姓名
		String xh=user.getArea(); //学号
		String fs= score+"";//分数
		String pj=score>=90?"优秀":score>=80?"良好":"合格";//等级
		long l1 = System.currentTimeMillis();
		File file = new File(CertImageUtil.class.getResource("/templ/cert_templ.png").getFile());
		BufferedImage read = ImageIO.read(file);
		Graphics g = read.getGraphics();
		g.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		g.setColor(new Color(0x885c24));
		g.drawString(zsbh, 640, 86);
		
		g.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		g.setColor(new Color(0x221e1f));
		g.drawString(xxAndXm, 84, 243);
		
		g.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		g.setColor(new Color(0x221e1f));
		g.drawString(xh, 130, 270);
		g.drawString(fs, 191, 380);
		g.drawString(pj, 405, 380);
		
		File genImage = new File(basePath+"/"+user.getUserid()%1000+"/"+user.getWxOpenid().substring(user.getWxOpenid().length()-8));
		if(!genImage.getParentFile().exists()) {
			genImage.getParentFile().mkdirs();
		}
		ImageIO.write(read, "png", genImage);
		System.err.println(System.currentTimeMillis()-l1);
	}
	public static String encodeCertCode(Long id,String openId) {
		return openId+"$"+(1382775151-id*462);
	}
	public static void main(String[] args) throws IOException {
		System.out.println(new File("temp/").getAbsolutePath());
		CertImageUtil.genCertImage(null, 1,"");
	}
}
