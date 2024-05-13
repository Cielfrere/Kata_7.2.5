package exercise.article;

import java.time.LocalDate;
import java.util.*;
import exercise.worker.Worker;
import exercise.worker.WorkerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class WorkerImplTest {
    private Worker worker;
    @Mock
    private Library  library;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        library = mock(Library.class);
        worker = new WorkerImpl(library);
    }
    @Test
    public void shouldAddNewArticles() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(
                "tittle1",
                "Что-то",
                "Кто-то",
                LocalDate.of(2024, 1, 6)));

        worker.addNewArticles(articles);

        verify(library).store(2024, articles);
    }

    @Test
    public void updateAddNewArticles() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(
                "tittle1",
                "Что-то",
                "Кто-то",
                LocalDate.of(2024, 1, 6)));

        worker.addNewArticles(articles);

        verify(library).store(2024, articles);
        verify(library, times(1)).updateCatalog();
    }
    @Test
    public void shouldNotAddNewArticles() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(
                null,
                null,
                null,
                null));

        worker.addNewArticles(articles);

        verify(library, never()).store(anyInt(), anyList());

    }
    @Test
    public void notUpdateAddNewArticles() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(
                null,
                null, null,
                null));

        worker.addNewArticles(articles);

        verify(library, never()).store(anyInt(), anyList());
        verify(library, never()).updateCatalog();
    }



    @Test
    public void testGetCatalog() {
        String catalog = worker.getCatalog();

        String expectedCatalog = "Список доступных статей:\n";

        assertEquals(expectedCatalog, catalog);
    }

    @Test
    public void shouldPrepareArticles() {
        Article article1 = new Article(
                "Tittle 1",
                "Content1",
                "Author 1",
                LocalDate.of(2021,
                        01,
                        01));
        List<Article> articles = Arrays.asList(article1);
        List<Article> preparedArticles = worker.prepareArticles(articles);

        assertEquals(1, preparedArticles.size());
        assertTrue(preparedArticles.contains(article1));
    }

    @Test
    public void shouldNotPrepareArticles() {
        Article article1 = new Article(
                null,
                "Content1",
                "Author 1",
                LocalDate.of(2021, 01, 01));
        List<Article> articles = Arrays.asList(article1);
        List<Article> preparedArticles = worker.prepareArticles(articles);

        assertEquals(0, preparedArticles.size());
        assertFalse(preparedArticles.contains(article1));
    }
    @Test
    public void automaticallyCreationDateOnArticleWithNull() {
            Article article2 = new Article(
                    "Tittle 2",
                    "С",
                    "Author 2",
                    null);

            List<Article> articles = Arrays.asList(article2);
            List<Article> preparedArticles = worker.prepareArticles(articles);

            assertEquals(1, preparedArticles.size());
            assertEquals(LocalDate.now(), article2.getCreationDate());
    }

    @Test
    public void uniqueTittleOfArticle() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(
                "tittle1",
                "Что-то",
                "Кто-то",
                LocalDate.of(2024, 1, 6)));
        articles.add(new Article(
                "tittle1",
                "Что-то",
                "Кто-то",
                LocalDate.of(2024, 1, 6)));

        worker.addNewArticles(articles);

        verify(library, never()).store(2024, articles);
    }
}