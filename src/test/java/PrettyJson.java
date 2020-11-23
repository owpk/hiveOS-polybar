public class PrettyJson {
    private int pos;
    private char[] chars;

    public static void main(String[] args) {
        String data = "{\"data\":[{\"twofa_required\":false,\"timezone\":\"Europe/Kiev\",\"asic_red_board_temp\":85,\"disabled_asics_count\":0,\"gpu_red_la\":1,\"charge_on_pool\":true,\"asics_count\":0,\"asic_red_fan\":100,\"gpu_red_cpu_temp\":70,\"stats\":{\"workers_invalid\":0,\"workers_total\":0,\"gpus_online\":0,\"asics_total\":0,\"workers_offline\":0,\"workers_low_asr\":0,\"gpus_overheated\":0,\"boards_overheated\":0,\"gpus_total\":0,\"asics_offline\":0,\"boards_online\":0,\"rigs_online\":0,\"asics_online\":0,\"workers_overheated\":0,\"workers_online\":0,\"rigs_offline\":0,\"gpus_offline\":0,\"rigs_total\":0,\"cpus_online\":0,\"workers_overloaded\":0,\"boards_total\":0,\"boards_offline\":0,\"power_draw\":0},\"autocreate_hash\":\"3ab3ff5597714fe164ebc264a9dc3de624b39195\",\"id\":277862,\"gpu_red_temp\":72,\"gpu_red_fan\":100,\"locked\":false,\"disabled_rigs_count\":0,\"asic_red_asr\":95,\"owner\":{\"name\":\"owpk\",\"me\":true,\"id\":207637,\"login\":\"owpk\"},\"gpu_red_mem_temp\":90,\"tag_ids\":[],\"asic_red_temp\":85,\"workers_count\":0,\"nonfree\":false,\"auto_tags\":false,\"asic_red_la\":3,\"money\":{\"price_per_rig\":3,\"monthly_price\":0,\"monthly_cost\":0,\"overdraft\":false,\"discount\":0,\"daily_price\":0,\"daily_cost\":0,\"price_per_asic\":2,\"balance\":0,\"is_free\":true,\"daily_use_rigs\":0,\"is_paid\":false,\"daily_use_asics\":0},\"trusted\":true,\"gpu_red_asr\":95,\"name\":\"owpk farm\",\"rigs_count\":0},{\"twofa_required\":false,\"timezone\":\"Asia/Tokyo\",\"asic_red_board_temp\":85,\"disabled_asics_count\":0,\"gpu_red_la\":1,\"charge_on_pool\":true,\"asics_count\":0,\"asic_red_fan\":100,\"gpu_red_cpu_temp\":70,\"stats\":{\"asr\":98.05,\"workers_invalid\":0,\"workers_total\":1,\"gpus_online\":2,\"asics_total\":0,\"workers_offline\":0,\"workers_low_asr\":0,\"gpus_overheated\":0,\"boards_overheated\":0,\"gpus_total\":2,\"asics_offline\":0,\"boards_online\":0,\"rigs_online\":1,\"asics_online\":0,\"workers_overheated\":0,\"workers_online\":1,\"rigs_offline\":0,\"gpus_offline\":0,\"rigs_total\":1,\"cpus_online\":0,\"workers_overloaded\":0,\"boards_total\":0,\"boards_offline\":0,\"power_draw\":203},\"autocreate_hash\":\"b99e5bc5419924d7513f5b48c2036efe04e349e4\",\"id\":277869,\"gpu_red_temp\":72,\"gpu_red_fan\":100,\"locked\":false,\"disabled_rigs_count\":0,\"asic_red_asr\":95,\"owner\":{\"name\":\"owpk\",\"me\":true,\"id\":207637,\"login\":\"owpk\"},\"gpu_red_mem_temp\":90,\"tag_ids\":[],\"asic_red_temp\":85,\"workers_count\":1,\"nonfree\":false,\"auto_tags\":true,\"asic_red_la\":3,\"money\":{\"price_per_rig\":3,\"monthly_price\":0,\"monthly_cost\":0,\"overdraft\":false,\"discount\":0,\"daily_price\":0,\"daily_cost\":0,\"price_per_asic\":2,\"balance\":0,\"cost_details\":[{\"monthly_price\":0,\"type_name\":\"Rig on brand pool\",\"amount\":1,\"monthly_cost\":0,\"type\":12,\"daily_cost\":0}],\"is_free\":true,\"daily_use_rigs\":0,\"is_paid\":false,\"daily_use_asics\":0},\"trusted\":true,\"gpu_red_asr\":95,\"hashrates_by_coin\":[{\"hashrate\":49810,\"algo\":\"ethash\",\"coin\":\"ETH\"}],\"name\":\"rx570worker\",\"default_fs\":{\"1\":3713618},\"rigs_count\":1,\"hashrates\":[{\"hashrate\":49810,\"algo\":\"ethash\"}]}],\"tags\":[]}";
        PrettyJson prettyJson = new PrettyJson();
        prettyJson.chars = data.toCharArray();
        prettyJson.pretty("");
    }

    //{"data" : 123}
    private void pretty(String tab) {
        term(tab);
        while (pos < chars.length) {
            if (chars[pos] == '}') {
                break;
            }
            else pos++;
            term(tab);
        }
    }

    private void term(String tab) {
        char current = chars[pos];
        if (current == '[') {
            System.out.print(current);
            pos++;
            if (chars[pos] == ']')
                System.out.print(chars[pos]);
            else {
                System.out.print("\n" + tab);
                pretty(tab);
            }
        } else if (current == '{') {
            pos++;
            String tempTab = tab;
            tab = tab + "\t";
            System.out.print(current + "\n" + tab);
            pretty(tab);
            char closingBracket = chars[pos];
            pos++;
            char next = 0;

            System.out.print("\n" + tempTab + closingBracket);
            if (pos < chars.length) {
                next = chars[pos];
                System.out.print((next == ']' ? "\n" + tempTab + next : next + "\n" + tempTab) );
            }
        } else if(current == ',') {
            System.out.print(current + "\n" + tab);
        }  else {
            if (current != '}')
            System.out.print(current);
        }
    }
}
