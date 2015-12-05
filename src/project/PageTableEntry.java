package project;

public class PageTableEntry 
{
	private int frameNumber;
	private boolean valid;

	public PageTableEntry() {
		// initially we do not have a valid mapping
		valid = false;
		frameNumber = -1;
	}

	public boolean getValidBit() {
		return valid;
	}

	//returns the address of something in physical memory
	public int getFrameNumber() {
		return frameNumber;
	}

	//maps to a frame in physical memory
	public void setMapping(int frameNumber) {
		this.frameNumber = frameNumber;
		valid = true;
	}
}