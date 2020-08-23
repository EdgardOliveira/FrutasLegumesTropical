package br.com.technologies.venom.frutastropicais;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetalheWrapper {

    @SerializedName("results")
    @Expose
    private List<Detalhe> results = null;
    @SerializedName("tfvcount")
    @Expose
    private Integer tfvcount;

    /**
     * No args constructor for use in serialization
     *
     */
    public DetalheWrapper() {
    }

    /**
     *
     * @param tfvcount
     * @param results
     */
    public DetalheWrapper(List<Detalhe> results, Integer tfvcount) {
        super();
        this.results = results;
        this.tfvcount = tfvcount;
    }

    public List<Detalhe> getResults() {
        return results;
    }

    public void setResults(List<Detalhe> results) {
        this.results = results;
    }

    public Integer getTfvcount() {
        return tfvcount;
    }

    public void setTfvcount(Integer tfvcount) {
        this.tfvcount = tfvcount;
    }
}