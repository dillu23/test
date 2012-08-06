import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.Random;
//import java
class newthread implements Runnable{
	Thread t;
	void send_udp(int num,int size,int itrs) throws Exception{
		String id=String.valueOf(num);
		int server_port = 9010;
		DatagramSocket s = new DatagramSocket();
		InetAddress local = InetAddress.getByName("124.124.247.5");
		String pad=" ";
		for(int i=90; i<size;i++){
			pad=pad+" ";
		}
		for (int i=1;i<=itrs;i++){
			String a="Method: ECHO\nId: "+id+"\nSeqno: " + i + "\nLength: 7000\nPadding, pad-character = SPACE"+pad;
			int msg_length=a.length();
			byte[] message = a.getBytes();
			DatagramPacket p = new DatagramPacket(message, msg_length,local,server_port);
			s.send(p);
			System.out.println("Message: "+i+" sent");
		}
		s.close();
		String messageStr="Method: STAT\nId: "+id;
		int msg_length=messageStr.length();
		byte[] message = messageStr.getBytes();
		s = new DatagramSocket();
		DatagramPacket p = new DatagramPacket(message, msg_length,local,server_port);
		s.send(p);
		System.out.println("Message STAT sent");
		long ed = new Date().getTime();
		long st = new Date().getTime();
		byte[] receivedata = new byte[1024];
		DatagramPacket recv_packet = new DatagramPacket(receivedata, receivedata.length);
		System.out.println("S: Receiving...");
		//s.setSoTimeout(15000);
		s.receive(recv_packet);
		String rec_str = new String(recv_packet.getData());
		System.out.println(rec_str);
		s.close();
		ed= new Date().getTime();
	}
	int num,size,itrs;
	newthread(int n,int s,int i){
		num=n;
		size=s;
		itrs=i;
		t=new Thread(this,"name");
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			send_udp(num,size,itrs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Thread Exception");
		}
	}
	
}

public class draintime {
	public static void main(String[] args){
		try{
			int size=1443;
			int itrs=24;
			Random rand=new Random();
			int num=rand.nextInt(100000000);
			int num1=rand.nextInt(100000000);
			newthread a=new newthread(num,size,itrs);
			newthread b=new newthread(num1,size,itrs);
			long st = new Date().getTime();
			a.t.start();
			Thread.sleep(100);
			long ed = new Date().getTime();
			System.out.println(ed-st);
			b.t.start();
			
			a.t.join();
			b.t.join();
			}
		catch(Exception e){

		}
	}
	
	
}
