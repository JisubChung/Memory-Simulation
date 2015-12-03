#include <stdio.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>

#define TLB_SIZE 16
#define PAGES 256
#define PAGE_MASK 255

#define PAGE_SIZE 256
#define OFFSET_BITS 8
#define OFFSET_MASK 255

#define MEMORY_SIZE PAGES * PAGE_SIZE

// Max number of characters per line of input file to read.
#define BUFFER_SIZE 10

struct tlbentry {
    unsigned char logical;
    unsigned char physical;
};

// TLB is kept track of as a circular array, with the oldest element being overwritten once the TLB is full.
struct tlbentry tlb[TLB_SIZE];
// number of inserts into TLB that have been completed. Use as tlbindex % TLB_SIZE for the index of the next TLB line to use.
int tlbindex = 0;

// pagetable[logical_page] is the physical page number for logical page. Value is -1 if that logical page isn't yet in the table.
int pagetable[PAGES];

signed char main_memory[MEMORY_SIZE];

// Pointer to memory mapped backing file
signed char *backing;

int max(int a, int b)
{
    if (a > b)
        return a;
    return b;
}

/* Returns the physical address from TLB or -1 if not present. */
int search_tlb(unsigned char logical_page) {
    int i;
    for (i = max((tlbindex - TLB_SIZE), 0); i < tlbindex; i++) {
        struct tlbentry *entry = &tlb[i % TLB_SIZE];
        
        if (entry->logical == logical_page) {
            return entry->physical;
        }
    }
    
    return -1;
}

/* Adds the specified mapping to the TLB, replacing the oldest mapping (FIFO replacement). */
void add_to_tlb(unsigned char logical, unsigned char physical) {
    struct tlbentry *entry = &tlb[tlbindex % TLB_SIZE];
    tlbindex++;
    entry->logical = logical;
    entry->physical = physical;
}

int main(int argc, const char *argv[])
{
    if (argc != 3) {
        fprintf(stderr, "Usage ./vmm backingstore input\n");
        exit(1);
    }
    
    
    // Fill page table entries with -1 for initially empty table.

    
    // Character buffer for reading lines of input file.

    
    // Data we need to keep track of to compute stats at end.

    
    
    while () {
  
        // TLB hit
        if () {
            // TLB miss
        } else {

            // Page fault

            }
            
            add_to_tlb(logical_page, physical_page);
        }
        
        printf("Virtual address: %d Physical address: %d Value: %d\n", logical_address, physical_address, value);
    }
    
    //Statistics
    
    return 0;
}
