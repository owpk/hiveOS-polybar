package org.owpk.entities.apiJson.farm;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.owpk.entities.AbsComponent;
import org.owpk.entities.jsonConfig.JsonConfig;

import java.util.List;

@JsonAutoDetect
@Getter
@Setter
@NoArgsConstructor
public class Farm extends AbsComponent {
    private Integer id;
    private String name;
    private String timezone;
    private boolean nonfree;
    private boolean twofa_required;
    private boolean trusted;
    private Integer gpu_red_temp;
    private Integer asic_red_temp;
    private Integer gpu_red_fan;
    private Integer asic_red_fan;
    private Integer gpu_red_asr;
    private Integer asic_red_asr;
    private Integer gpu_red_la;
    private Integer asic_red_la;
    private Integer gpu_red_cpu_temp;
    private Integer gpu_red_mem_temp;
    private Integer asic_red_board_temp;
    private String autocreate_hash;
    private boolean locked;
    private List<String> tag_ids;
    private boolean auto_tags;
    @JsonProperty
    @JsonIgnore
    private DefaultFs default_fs;
    private Integer workers_count;
    private Integer rigs_count;
    private Integer asics_count;
    private Integer disabled_rigs_count;
    private Integer disabled_asics_count;
    @JsonProperty
    @JsonIgnore
    private Owner owner;
    @JsonProperty
    @JsonIgnore
    private Money money;
    @JsonProperty
    @JsonIgnore
    private Stats stats;
    @JsonProperty
    @JsonIgnore
    private List<HashRates> hashrates;
    @JsonProperty
    @JsonIgnore
    private List<HashRatesByCoin> hashrates_by_coin;
    private boolean charge_on_pool;

}