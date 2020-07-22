/**
 * 
 */
package com.aequilibrium.transformers.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aequilibrium.transformers.TransformersController;
import com.aequilibrium.transformers.data.Transformer;
import com.aequilibrium.transformers.repo.TransformerRepository;
import com.aequilibrium.transformers.util.TransformerUtil;

/**
 * @author Abhay
 * The service for Transformer
 */

/**
 * @author Abhay
 *
 */
/**
 * @author Abhay
 *
 */
@Service
public class TransformerService {
	private final static Logger log = LoggerFactory.getLogger(TransformerService.class);

	/**
	 *  Transformer Repository
	 */
	@Autowired
	TransformerRepository repository;
	
	/**
	 * elimination count for Autobots team 
	 */
	int eliminationCountA = 0;
	
	/**
	 * elimination count for Decepticons team
	 */
	int eliminationCountD= 0 ;

	
	/**
	 * service method to create transformer
	 * @param transformers
	 * @return
	 */
	public boolean createTransformer( Transformer[] transformers) {
		log.info("In TransformerService.createTransformer()...");
		
		for (Transformer t : transformers) {
			if(TransformerUtil.checkTransformerType(t)) {
				Transformer transformer = TransformerUtil.getOverallTransformerRating(t);
				repository.save(transformer);
				log.info("Transformer Saved in repository");
			}else {
				log.info("Transformer Saved failed in repository");
				return false;
			}
			
		} 
		return true;
	}
	
	/**
	 * service method to update transformer
	 * @param t
	 * @return
	 */
	public boolean updateTransformer(Transformer t) {
		log.info("In TransformerService.updateTransformer()...");
		
		if(TransformerUtil.checkTransformerType(t)) {
		
			Transformer newTransformer = TransformerUtil.getOverallTransformerRating(t);
			
			Transformer newT = repository.save(newTransformer);
			log.info("Transformer updated in repository");
			
			return true;
		}
		else {
			log.info("Transformer updated failed in repository");
			return false;
		}
	}

	/**
	 * service method to get transformers list
	 * @return
	 */
	public List<Transformer> getTransformersList(){
		log.info("In TransformerService.getTransformersList()...");
		
		final List transformerList = new ArrayList();
		repository.findAll().forEach(transformer -> transformerList.add(transformer));
		log.info("Transformers List:"+ transformerList);
		return transformerList;
	}

	/**
	 * service method to delete the transformer
	 * @param transformerId
	 */
	public void deleteTransformer(int transformerId) {
		log.info("In TransformerService.deleteTransformer()...");
		
		repository.deleteById(transformerId);
		log.info("Transformer deleted from repository");
		
	}

	/**
	 * service method to determine the winning team 
	 * @param tranformerIDList
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> determineWiningTeam(ArrayList<Integer> tranformerIDList) throws Exception{
		log.info("In TransformerService.determineWiningTeam()...");
		
		List transformersAList = new ArrayList<>();
		List transformersDList = new ArrayList<>();
		List tranformersList = new ArrayList<>();

		
		for(int i=0;i< tranformerIDList.size();i++) {
			int transformerId = tranformerIDList.get(i);
			if(repository.existsById(transformerId)) {
				Optional<Transformer> opt = repository.findById(transformerId);
				Transformer t = opt.get();
				if(t.getTransfomerType().equalsIgnoreCase("A")) {
					transformersAList.add(t);
				}else if(t.getTransfomerType().equalsIgnoreCase("D")) {
					transformersDList.add(t);
					
				}

				tranformersList.add(t);	// for reference
			}
			else {
				throw new Exception();
			}
		}
		
		// Rank all the transformers based on overall ranking score. 
		Collections.sort(transformersAList);
		Collections.sort(transformersDList);

		log.info("Transformers Autobots sorted by Rank:"+transformersAList);
		log.info("Transformers Decepticons sorted by Rank:"+transformersDList);

		// determine number of battles
		int numBattles = (transformersAList.size() < transformersDList.size())? transformersAList.size() : transformersDList.size();
		log.info("Number of battles to fight:"+numBattles);
		//apply battle rules
		applyBattleRules(transformersAList,transformersDList, numBattles);

		// winning team with largest elimination count is
		String winnerTeam = (eliminationCountA > eliminationCountD)? "Decepticons":"Autobots";
		String loosingTeam = (eliminationCountA > eliminationCountD)? "Autobots":"Decepticons";
		
		log.info("Winning Team:"+ winnerTeam);
		
		List<Transformer> loosingSurvivorsList = (winnerTeam.equalsIgnoreCase("Decepticons"))? transformersAList : transformersDList;
		List<Transformer> winnersList = (winnerTeam.equalsIgnoreCase("Decepticons"))? transformersDList: transformersAList ;
		
		List<String> loosingSurvivors = findLoosingSurvivors(loosingSurvivorsList);
		List <String> winners = findWinners(winnersList);
		
		HashMap battleOutput = new HashMap();
		battleOutput.put("numBattles", numBattles);
		battleOutput.put("winningTeam", winnerTeam);
		battleOutput.put("loosingTeam",  loosingTeam);
		battleOutput.put("winners", winners);
		battleOutput.put("loosingSurvivors", loosingSurvivors);
		
		log.info("Loosing Team:"+ loosingTeam);
		return battleOutput;
	}
	
	/**
	 * method to find winners 
	 * @param winnersList
	 * @return
	 */
	private List<String> findWinners(List<Transformer> winnersList) {
		return winnersList.stream().filter( transformer -> transformer.isEliminated() ==false).
				collect(Collectors.toList()).stream().map(transformer -> transformer.getTransformerName()).collect(Collectors.toList())
				;
	}

	/**
	 * method to find loosers
	 * @param loosingSurvivorsList
	 * @return
	 */
	private List<String> findLoosingSurvivors(List<Transformer> loosingSurvivorsList) {
		return loosingSurvivorsList.stream().filter( transformer -> transformer.isEliminated() ==false).
				collect(Collectors.toList()).stream().map(transformer -> transformer.getTransformerName()).collect(Collectors.toList());
	}

	/**
	 * method that has battle rules logic
	 * @param transformersAList
	 * @param transformersDList
	 * @param numBattles
	 */
	private void applyBattleRules(List transformersAList, List transformersDList, int numBattles) {
		log.info("In TransformerService.applyBattleRules()...");
		
		for(int i=0;i<numBattles;i++) {
			//face off condition
			Transformer ta =(Transformer) transformersAList.get(i);
			Transformer td = (Transformer) transformersDList.get(i);
			
			String taName = ta.getTransformerName();
			String tdName = td.getTransformerName();
			
			log.info("Based on Ranking Autobot "+taName+" is faced off with Decepticons "+ tdName);
			
			// condition when Prime and Predaking face each other / or duplicate of each other, end the game. 
			if(taName.equalsIgnoreCase("Optimus Prime") && tdName.equalsIgnoreCase("Predaking")
				|| taName.equalsIgnoreCase("Optimus Prime") && tdName.equalsIgnoreCase("Optimus Prime") 
				|| tdName.equalsIgnoreCase("Predaking") && taName.equalsIgnoreCase("Predaking")){
					break;
				}
				
				
			//if transfomer name is Prime / Predaking, he is winner ie. eliminate the opponent. 
			if(taName.equalsIgnoreCase("Optimus Prime") )
				eliminateTransformersD(transformersDList,i);
			if(tdName.equalsIgnoreCase("Predaking")) 
				eliminateTransformerA(transformersAList,i);
			
			
			if( ta.getCourage() < (td.getCourage() - 4 ) && ta.getStreanth() < (td.getStreanth() - 3)) {
				// ta is eliminated by opponent so remove it from list
				eliminateTransformerA(transformersAList,i);
			}
			else {
				if( ta.getSkill() <= (td.getSkill() - 3) ) {
					// ta has less skills so it needs tobe removed
					eliminateTransformerA(transformersAList, i);
					
				}else {

					// event of tie consider both transformers as destroyed.
					if(ta.getRating() == td.getRating()) {
						eliminateTransformerA(transformersAList, i);
						eliminateTransformersD(transformersDList,i);
					} else {
						if( ta.getRating() < td.getRating()) {
							eliminateTransformerA(transformersAList, i);
						}
						else {
							eliminateTransformersD(transformersDList,i);
						}
						
					}

				}

			}
		}
		
	}

	/**
	 * method to eliminate Autobots transformer
	 * @param transformersAList
	 * @param index
	 */
	public void eliminateTransformerA(List transformersAList, int index) {
		transformersAList = TransformerUtil.eliminateTheTransformer(transformersAList, index);
		eliminationCountA++;
	}
	/**
	 * method to eliminate Decepticons transformer
	 * @param transformersDList
	 * @param index
	 */
	public void eliminateTransformersD(List transformersDList, int index) {
		transformersDList = TransformerUtil.eliminateTheTransformer(transformersDList, index);
		eliminationCountD++;
		
	}

}
