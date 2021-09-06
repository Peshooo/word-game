package com.wordgame.recordsstorage.service.deleter;

import com.wordgame.recordsstorage.configuration.GameModesConfiguration;
import com.wordgame.recordsstorage.repository.GameRecordsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = {GameModesConfiguration.class, OldGameRecordsDeleter.class})
@RunWith(SpringRunner.class)
public class OldGameRecordsDeleterTest {
    @Autowired
    private List<String> gameModes;

    @MockBean
    private GameRecordsRepository gameRecordsRepository;

    @Autowired
    private OldGameRecordsDeleter oldGameRecordsDeleter;

    @Test
    public void testScheduledDeleteCallsRepositoryDelete() {
        oldGameRecordsDeleter.deleteOldRecords();

        Mockito.verify(gameRecordsRepository, Mockito.times(gameModes.size())).update(Mockito.any());
    }
}
