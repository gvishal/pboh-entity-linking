package index;


// Class that loads and keeps all indexes.
class AllIndexesBox(whichIndexes : String)  extends java.io.Serializable {

  val wordFreqDictPath = "/data/pboh-data/pboh_data/textFromAllWikipedia2014Feb.txt_word_frequencies"
  var wordFreqDict : WordFreqDict = null
	
  val wordEntityProbsIndexPath = "/data/pboh-data/pboh_data/textFromAllWikipedia2014Feb.txt_w_e_counts_window_50"
  var wordEntityProbsIndex : WordEntityProbsIndex = null

  val wikipEntityCooccurrIndexPath = "anchorsListFromEachWikiPage.txt_dev_index"
  var wikipEntityCooccurrIndex : EntityCooccurrenceIndex = null

  val wikilinksEntityCooccurrIndexPath = "anchorsListFromEachWikilinksDoc.txt_dev_index"
  var wikilinksEntityCooccurrIndex : EntityCooccurrenceIndex = null
	
  val mentionEntsFreqIndexPath = "mek-top-freq-crosswikis-plus-wikipedia-lowercase-top64.txt"
  var mentionEntsFreqIndex : MentionEntitiesFrequencyIndex = null
	
  val entIDNameIndexPath = "enwiki-titles.txt" 
  var entIDToNameIndex : EntIDToNameIndex = null
  var entNameToIDIndex : EntNameToIDIndex = null

  val redirectsFilePath = "enwiki-redirect-normalized.txt"
  var redirectIndex : RedirectPagesIndex = null
	
  val freebaseWikiMapFile = "wikip_freebase_map.txt";
  var freebaseToWikiIDIndex : FreebaseWikipIndex = null

  // On DCO cluster: root = "/media/hofmann-scratch/Octavian/el-lbp-marina/marinah/wikipedia/"
  val root = "/data/pboh-data/pboh_data/"


  ////////////// Constructor ///////////////////
	    
  entNameToIDIndex = new EntNameToIDIndex
  entNameToIDIndex.load(root + entIDNameIndexPath);
  
  entIDToNameIndex = new EntIDToNameIndex
  entIDToNameIndex.load(root + entIDNameIndexPath);     
    
  
  if (whichIndexes.contains("wikipEntityCooccurrIndex")) {
    wikipEntityCooccurrIndex = new EntityCooccurrenceIndex("wikipedia_index")
    wikipEntityCooccurrIndex.load(root + wikipEntityCooccurrIndexPath, true);
	
    wikilinksEntityCooccurrIndex = new EntityCooccurrenceIndex("wikilinks_index")
  }
  
  if (whichIndexes.contains("mentionEntsFreqIndex")) {
    mentionEntsFreqIndex = new MentionEntitiesFrequencyIndex
    mentionEntsFreqIndex.load(root + mentionEntsFreqIndexPath, entIDToNameIndex);
  }
  
  if (whichIndexes.contains("wordFreqDict")) {
    wordFreqDict = new WordFreqDict();
    wordFreqDict.load(wordFreqDictPath);
  }

  if (whichIndexes.contains("wordEntityProbs")) {
    // most time consuming
    wordEntityProbsIndex = new WordEntityProbsIndex
    // only load it once, subsequently reuse the old index.
    // wordEntityProbsIndex.load(wordEntityProbsIndexPath, wordFreqDict);
  }
  
  if (!whichIndexes.contains("mentionEntsFreqIndex") && whichIndexes.contains("entIDToNameIndex")) {
    entIDToNameIndex = new EntIDToNameIndex
    entIDToNameIndex.load(root + entIDNameIndexPath); 
  }
	
  if (whichIndexes.contains("redirectIndex")) {
    redirectIndex = new RedirectPagesIndex
    redirectIndex.load(root + redirectsFilePath);
  }
}
