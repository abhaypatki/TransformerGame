/**
 * 
 */
package com.aequilibrium.transformers.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aequilibrium.transformers.TransformersController;

/**
 * @author Abhay
 * The class for Transformer 
 */

@Entity
public class Transformer implements Comparable<Transformer>{
	private final static Logger log = LoggerFactory.getLogger(Transformer.class);

	@Id
	@GeneratedValue
	private int transformerId;
	
	private String transformerName;
	private String transfomerType;
	private int streanth;
	private int intelligance;
	private int speed;
	private int indurance;
	private int rank;
	private int courage;
	private int firepower;
	private int skill;
	
	private int rating;
	private boolean isEliminated = false;
 
	@Override
	public String toString() {
		return "transformerId:"+transformerId+ " transformerName:"+transformerName+" transfomerType:"+transfomerType+" streanth:"+streanth+ " intelligance:"+intelligance
				+ " speed:"+speed +" indurance:"+indurance +" rank:"+rank +" courage:" +courage +" firepower:"+firepower +" skill:"+skill
				+ " rating:"+rating + " isEliminated:"+isEliminated;
	}
	/**
	 * default constructor
	 */
	public Transformer() {
		
	}
	
	/**
	 * parameterized constructor for transformer
	 * @param id
	 * @param transformerName
	 * @param transfomerType
	 * @param streanth
	 * @param intelligance
	 * @param speed
	 * @param indurance
	 * @param rank
	 * @param courage
	 * @param firepower
	 * @param skill
	 */
	public Transformer(int id,String transformerName, String transfomerType, int streanth, int intelligance, int speed,
			int indurance,int rank, int courage, int firepower, int skill) {
		
		this.transformerId = id;
		this.transformerName = transformerName;
		this.transfomerType = transfomerType;
		this.streanth = streanth;
		this.intelligance = intelligance;
		this.speed = speed;
		this.indurance = indurance;
		this.rank = rank;
		this.courage = courage;
		this.firepower = firepower;
		this.skill = skill;
		this.rating = rating;
	}

	/**
	 * @return the transformerId
	 */
	public int getTransformerId() {
		return transformerId;
	}

	/**
	 * @param transformerId the transformerId to set
	 */
	public void setTransformerId(int id) {
		this.transformerId = id;
	}

	/**
	 * @return the transformerName
	 */
	public String getTransformerName() {
		return transformerName;
	}

	/**
	 * @param transformerName the transformerName to set
	 */
	public void setTransformerName(String transformerName) {
		this.transformerName = transformerName;
	}
	
	/**
	 * @return the transfomerType
	 */
	public String getTransfomerType() {
		return transfomerType;
	}

	/**
	 * @param transfomerType the transfomerType to set
	 */
	public void setTransfomerType(String transfomerType) {
		this.transfomerType = transfomerType;
	}

	/**
	 * @return the streanth
	 */
	public int getStreanth() {
		return streanth;
	}

	/**
	 * @param streanth the streanth to set
	 */
	public void setStreanth(int streanth) {
		this.streanth = streanth;
	}

	/**
	 * @return the intelligance
	 */
	public int getIntelligance() {
		return intelligance;
	}

	/**
	 * @param intelligance the intelligance to set
	 */
	public void setIntelligance(int intelligance) {
		this.intelligance = intelligance;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the indurance
	 */
	public int getIndurance() {
		return indurance;
	}

	/**
	 * @param indurance the indurance to set
	 */
	public void setIndurance(int indurance) {
		this.indurance = indurance;
	}

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @return the courage
	 */
	public int getCourage() {
		return courage;
	}

	/**
	 * @param courage the courage to set
	 */
	public void setCourage(int courage) {
		this.courage = courage;
	}

	/**
	 * @return the firepower
	 */
	public int getFirepower() {
		return firepower;
	}

	/**
	 * @param firepower the firepower to set
	 */
	public void setFirepower(int firepower) {
		this.firepower = firepower;
	}

	/**
	 * @return the skill
	 */
	public int getSkill() {
		return skill;
	}

	/**
	 * @param skill the skill to set
	 */
	public void setSkill(int skill) {
		this.skill = skill;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * method to compare the transformers based on their ranking
	 */
	@Override
	public int compareTo(Transformer o) {

		return o.getRank() - this.getRank()  ;
	}

	/**
	 * @return the isEliminated
	 */
	public boolean isEliminated() {
		return isEliminated;
	}

	/**
	 * @param isEliminated the isEliminated to set
	 */
	public void setEliminated(boolean isEliminated) {
		this.isEliminated = isEliminated;
	}

}
