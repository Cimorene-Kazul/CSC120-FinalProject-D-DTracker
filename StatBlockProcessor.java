public class StatBlockProcessor {
    String statBlock;

    public StatBlockProcessor(String statBlock){
        this.statBlock = statBlock.trim();
    }

    public String getName(){
        return(this.statBlock.substring(0, this.statBlock.indexOf(" \n ")));
    }

    public Integer getQuanity(String preceeding){
        boolean isNext = false;
        for (String chunk: this.statBlock.split(" ")){
            if (isNext){
                return Integer.parseInt(chunk);
            }
            if (chunk == preceeding){
                isNext = true;
            }
        }
        return 0;
    }
}
