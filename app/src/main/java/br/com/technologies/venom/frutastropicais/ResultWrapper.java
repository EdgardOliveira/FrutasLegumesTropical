package br.com.technologies.venom.frutastropicais;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultWrapper {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("tfvcount")
    @Expose
    private Integer tfvcount;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResultWrapper() {
    }

    /**
     *
     * @param tfvcount
     * @param results
     */
    public ResultWrapper(List<Result> results, Integer tfvcount) {
        super();
        this.results = results;
        this.tfvcount = tfvcount;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Integer getTfvcount() {
        return tfvcount;
    }

    public void setTfvcount(Integer tfvcount) {
        this.tfvcount = tfvcount;
    }

}