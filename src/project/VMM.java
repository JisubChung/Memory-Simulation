package project;

//Your Test JAVA Program, to be completed by you. 

import java.io.*;

public class VMM
{
	private static final int PAGE_TABLE_ENTRIES = 256; //256
	private static final int NUMBER_OF_FRAMES = 256; //256
	//private static final int PHYSICAL_MEMORY_SIZE = 256*256;
	private static final int PHYSICAL_MEMORY_SIZE = Frame.FRAME_SIZE * NUMBER_OF_FRAMES;
	private static final int PAGE_SIZE = 256;  //256
	//private static final int NUMBER_OF_FRAMES = PHYSICAL_MEMORY_SIZE / PAGE_SIZE;	
	private static final int TLB_SIZE = 16;

	private File fileName;				/* the file representing the simulated  disk */
	private RandomAccessFile disk = null;	/* the input file of logical addresses */
	private BufferedReader r = null;

	private int virtualAddress;			/* the virtual address being translated */
	private int physicalAddress;			/* the physical address */

	private int pageNumber;				/* virtual page number */
	private int frameNumber;				/* physical frame number */
	private int offset;					/* offset in page/frame */

	private byte value;					/* the value stored at the physical address */

	private int nextFrameNumber;			/* the next available frame number */
	private int nextTLBEntry;			/* the next available entry in the TLB */

	private PageTableEntry[] pageTable;	/* the page table */
	private Frame[] physicalMemory;		/* physical memory (organized in frames) */

	private TLBEntry[] TLB;				/* the TLB */

	private byte[] buffer;				/* the buffer for storing the page from disk */

	private int pageFaults;				/* the number of page faults */
	private int TLBHits;				/* the number of TLB hits */
	private int numberOfAddresses;		/* the number of addresses that were translated */

	/** 
	 * Constructor.
	 *
	 * Intializes the various data structures including:
	 *
	 * (1) Page table
	 * (2) TLB
	 * (3) Physical memory
	 */
	public VMM() {
		// create the page table


		// create the TLB


		// allocate the physical memory


		// initialize the next frame number
		nextFrameNumber = 0;

		// initialize the next available entry in the TLB
		nextTLBEntry = 0;

		// allocate a temporary buffer for reading in from disk
		buffer = new byte[PAGE_SIZE];

		// initialize the statistics counters
		pageFaults = 0;
		TLBHits = 0;
	}
	

	/**
	 * Extract the page number.
	 */
	public int getPageNumber(int virtualAddress) {
		return  (virtualAddress & 0x0000ff00) >> 8;
	}

	/**
	 * Extract the offset.
	 */
	public int getOffset(int virtualAddress) {
		return (virtualAddress & 0x000000ff);
	}

	/**
	 * Return the number of the next available frame.
	 * This just uses a simple approach of assigning
	 * the next frame in memory.
	 */
	public int getNextFrame() {
		return nextFrameNumber++;
	}

	/**
	 * Check the TLB for a mapping of
	 * page number to physical frame
	 *
	 * @return -1 if no mapping or the frame number >= 0
	 */
	public int checkTLB(int pageNumber) {
		int frameNumber = -1;

		/**
		 * A "real" TLB would use associative memory
		 * where we could check all values in the
		 * TLB memory at the same time. We have to 
		 * in fact do a linear search of our TLB
	 	 */


		return frameNumber;
	}

	/**
	 * Maps a page number to its frame number in the TLB.
	 */
	public void setTLBMapping(int pageNumber, int frameNumber) {
		// establish the mapping


		/**
		 * Update the next TLB entry.
		 *
		 * This uses a very simple FIFO approach for
		 * managing entries in the TLB.
		 */

	}
		

	/**
	 * Determine the physical address of a given virtual address
	 */
	public int getPhysicalAddress(int virtualAddress) throws java.io.IOException {
		// determine the page number

		//System.out.println("Page number = " + pageNumber);

		// determine the offset

		//System.out.println("offset = " + offset);

		/**
		 * First check the TLB. We only have to run the 
		 * algorithm to extract the frame in the case of
		 * a TLB miss. Where we have a TLB hit, we can
		 * directly obtain the associated frame from the
		 * given page number.
		 */
		if () {  /** TLB Miss **/
			// Check the page table
			if () { /** Page Table Hit **/
				
			}
			else { 	/** Page Fault **/

				// get a free frame

				/**
				 * The following performs a
				 * demand page from disk.
				 *
				 * It does so by:
				 * (1) Reads the page from BACKING_STORE;
				 * (2) updates the page table;
				 * (3) updates the TLB.
				 */

				// seek to the appropriate page in the BACKING_STORE file

				// read in a page-size chunk from BACKING_STORE
				// into a temporary buffer


				// copy the contents of the buffer 
				// to the appropriate physical frame


				// now establish a mapping
				// of the frame in the page table

				//System.out.print(" * ");
			}

			// lastly, update the TLB

		}

		//System.out.println("Frame = " + frameNumber);

		// construct the physical address
		physicalAddress = (frameNumber << 8) + offset;

		return physicalAddress;
	}

	/**
	 * Returns the signed byte value at the specified physical address.
	 */
	public byte getValue(int physicalAddress) throws java.io.IOException {
		/* disk.seek(virtualAddress); */
		// read() returns a byte, but since bytes 
		// in Java are signed, we use an integer
		// to store its value to obtain the signed
		// value of the byte
		/* return disk.read(); */

		/**
		 * Essentially, the code below performs the following:
		 * return physicalMemory[frameNumber].readWord(offset);
		 */
		return physicalMemory[((physicalAddress & 0x0000ff00) >> 8)].readWord(physicalAddress & 0x000000ff);
	}

	/** 
	 * Generate statistics.
	 */
	public void generateStatistics() {
	}

	/**
	 * The primary method that runs the translation of logical to physical addresses.
	 */
	public void runTranslation(String inputFile) throws java.io.IOException {
		//Use a try-catch block since the logic involves IO operations.
		try {
			r = new BufferedReader(new FileReader(inputFile));
             fileName = new File("BACKING_STORE");
             disk = new RandomAccessFile(fileName, "r");
			String stringValue;
			
			while ( (stringValue = r.readLine()) != null) {
				// read in the virtual address
				virtualAddress = Integer.parseInt(stringValue);

				// obtain the corresponding physical address
				physicalAddress = getPhysicalAddress(virtualAddress);

				numberOfAddresses++;
			
				// get the value stored at the physical address	
				value = getValue(physicalAddress);
				
				System.out.println("Virtual address: " + virtualAddress + " Physical address: " + physicalAddress + " Value: " + value);
			}

			generateStatistics();
		}
		catch (java.io.IOException ioe) {
			System.err.println(ioe);
		}
		finally {
			disk.close();
			r.close();
		}
	}

	public static void main(String[] args) throws java.io.IOException {
		if (args.length != 1) {
			System.err.println("Usage: java VMM <input file>");
			System.exit(-1);
		}
		else {

			//Ready to run runTranslation() in VMM.
		}
	}
}