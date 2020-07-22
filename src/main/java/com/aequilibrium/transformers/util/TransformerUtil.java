/**
 * 
 */
package com.aequilibrium.transformers.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aequilibrium.transformers.data.Transformer;
import com.aequilibrium.transformers.service.TransformerService;

/**
 * @author Abhay
 * The utility class for Transformer
 */
public class TransformerUtil {
	private final static Logger log = LoggerFactory.getLogger(TransformerUtil.class);
	
	/** get overall rating for the transformer
	 * @param t
	 * @return
	 */
	public static Transformer getOverallTransformerRating(Transformer t) {
		 t.setRating(t.getStreanth()+t.getIntelligance()+t.getSpeed()+t.getIndurance()+t.getFirepower());
		 return t; 
	}
	/**
	 * method to check the transformer type ie. either Autobot (A) or Decepticons (D)
	 * @param t
	 * @return
	 */
	public static boolean checkTransformerType(Transformer t) {
		if(t.getTransfomerType().trim().equalsIgnoreCase("D") || t.getTransfomerType().trim().equalsIgnoreCase("A")) {
			return true;
		}
		return false; 
		
	}

	/**
	 * method to eliminate the transformer from the list
	 * @param tlist
	 * @param index
	 * @return
	 */
	public static List eliminateTheTransformer(List<Transformer> tlist, int index) {
		Transformer t = tlist.get(index);
		t.setEliminated(true);
		log.info("Transformer Eliminated is:"+t.getTransformerName());
		return tlist;
	}

}
