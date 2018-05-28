package com.mkyong;

import java.util.Comparator;

public class BidderComparator  implements Comparator<Bidder>{

	@Override
	public int compare(Bidder bidder1, Bidder bidder2) {

		int brandCompare = bidder1.getBrand().compareTo(bidder2.getBrand());
		System.out.println("Brand Compare:::: " + brandCompare);
		if (brandCompare == 0) {
			if (bidder1.getQuote() == bidder2.getQuote()) {
				return 0;
			} else if (bidder1.getQuote() > bidder2.getQuote()) {
				return -1;
			} else {
				return 1;
			}
		}

		return -1;
	}
}
