package io.pivotal.soloist;

import static org.assertj.core.api.Assertions.assertThat;

import io.pivotal.model.soloist.Soloist;
import org.junit.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;


public class YamlParserTest {

    private String SOLOIST_YAML =
            "recipes:\n" +
            "\n" +
            "# base (required by sprout)\n" +
            "- sprout-base\n" +
            "- sprout-base::bash_it\n" +
            "- sprout-base::homebrew\n" +
            "- sprout-homebrew\n" +
            "- sprout-vim\n" +
            "\n" +
            "# apps\n" +
            "- sprout-osx-apps::iterm2\n" +
            "- sprout-osx-apps::shiftit\n" +
            "- sprout-osx-apps::tunnelblick\n" +
            "\n" +
            "# settings\n" +
            "- sprout-osx-settings\n" +
            "- sprout-osx-settings::dock_preferences\n" +
            "- sprout-terminal\n" +
            "- sprout-ssh::known_hosts_github\n" +
            "\n" +
            "# development (general)\n" +
            "- sprout-base::workspace_directory\n" +
            "- sprout-git\n" +
            "- sprout-git::default_editor\n" +
            "- sprout-git::projects\n" +
            "- sprout-git::git_scripts\n" +
            "\n" +
            "# development (rails)\n" +
            "- sprout-rbenv\n" +
            "- sprout-ruby\n" +
            "- sprout-mysql\n" +
            "- sprout-postgresql\n" +
            "\n" +
            "# apps (editors)\n" +
            "- sprout-osx-apps::textmate_preferences\n" +
            "- sprout-osx-apps::sublime_text\n" +
            "- sprout-jetbrains-editors::rubymine\n" +
            "- sprout-jetbrains-editors::intellij\n" +
            "\n" +
            "node_attributes:\n" +
            "  homebrew:\n" +
            "    version: \"7fca726d2728f624e372be269d0065205cb642c8\"\n" +
            "  sprout:\n" +
            "    git:\n" +
            "      domain: pivotallabs.com\n" +
            "      authors:\n" +
            "        - initials: ah\n" +
            "          name: Abhijit Hiremagalur\n" +
            "          username: abhi\n" +
            "        - initials: bc\n" +
            "          name: Brian Cunnie\n" +
            "          username: cunnie\n" +
            "        - initials: jrhb\n" +
            "          name: Jonathan Barnes\n" +
            "        - initials: lw\n" +
            "          name: Luke Winikates\n" +
            "          username: lwinikates\n" +
            "      projects:\n" +
            "        -\n" +
            "          name: sprout-wrap\n" +
            "          url: https://github.com/pivotal-sprout/sprout-wrap.git\n" +
            "    terminal:\n" +
            "      default_profile: 'Pro'\n" +
            "    settings:\n" +
            "      clock_format: EEE MMM d  h:mm:ss a\n" +
            "    dock_preferences:\n" +
            "      orientation: 'left'\n" +
            "      auto_hide: true\n" +
            "      clear_apps: true\n" +
            "      tile_size: 35\n" +
            "      magnification: false\n" +
            "    homebrew:\n" +
            "      formulas:\n" +
            "        - ctags-exuberant\n" +
            "        - ag\n" +
            "        - chromedriver\n" +
            "        - imagemagick\n" +
            "        - node\n" +
            "        - pstree\n" +
            "        - qt\n" +
            "        - ssh-copy-id\n" +
            "        - tmux\n" +
            "        - tree\n" +
            "        - watch\n" +
            "        - wget\n" +
            "        - rbenv-binstubs\n" +
            "        - golang\n" +
            "      casks:\n" +
            "        - ccmenu\n" +
            "        - firefox\n" +
            "        - flycut\n" +
            "        - rowanj-gitx\n" +
            "        - github\n" +
            "        - google-chrome\n" +
            "        - google-drive\n" +
            "        - google-hangouts\n" +
            "        - textmate\n" +
            "        - vagrant\n" +
            "        - virtualbox\n" +
            "        - xquartz\n" +
            "        - xscope\n" +
            "        - screenhero\n" +
            "        - slack";

    @Test
    public void shouldParseSoloistYaml() throws Exception {
        Yaml yaml = new Yaml();

        Soloist soloist = yaml.loadAs(SOLOIST_YAML, Soloist.class);

        assertThat(soloist.getNode_attributes().getSprout().getHomebrew().getFormulas()).hasSize(14);
        assertThat(soloist.getNode_attributes().getSprout().getHomebrew().getCasks()).hasSize(15);
    }

    @Test
    public void shouldDumpSoloistYaml() throws Exception {
        DumperOptions printOptions = new DumperOptions();
        printOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(printOptions);

        String yamlDump = yaml.dump(yaml.loadAs(SOLOIST_YAML, Soloist.class));

        System.out.println(yamlDump);
        assertThat(yamlDump).contains("ctags-exuberant");
    }

}
