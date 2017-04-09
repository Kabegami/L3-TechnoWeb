package test;

import static org.junit.Assert.*;
import org.junit.Test;

import services.MailRecovery;

public class MailTest {
	
	@Test
	public void testSendPassword(){
		MailRecovery.sendPassword("noreply.twister06@gmail.com");
	}
}
