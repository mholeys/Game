package packets;

public abstract class PacketData {

	protected byte[] trimType(byte[] dataIn) {
		byte[] dataOut = new byte[dataIn.length-4];
		System.arraycopy(dataIn, 4, dataOut, 0, dataOut.length);
		return dataOut;
	}
	
	protected String[] trimToAttributes(byte[] data) {
		String[] attributes = new String(data).replace((char) 0, ' ').split(";");
		//Removes all empty bytes at the end of the data received
		attributes[attributes.length-1] = attributes[attributes.length-1].trim();
		return attributes;
	}
	
}
