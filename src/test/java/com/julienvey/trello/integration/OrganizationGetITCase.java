package com.julienvey.trello.integration;

import com.julienvey.trello.Trello;
import com.julienvey.trello.TrelloHttpClient;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.Member;
import com.julienvey.trello.domain.Organization;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;
import com.julienvey.trello.impl.http.AsyncTrelloHttpClient;
import com.julienvey.trello.impl.http.RestTemplateHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Parameterized.class)
public class OrganizationGetITCase {
    private static final String TEST_APPLICATION_KEY = "db555c528ce160c33305d2ea51ae1197";
    public static final String ORG_ID = "518baaaa815af84031004375";

    private Trello trello;

    private TrelloHttpClient httpClient;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{new ApacheHttpClient()}, {new AsyncTrelloHttpClient()}, {new RestTemplateHttpClient()}});
    }

    public OrganizationGetITCase(TrelloHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Before
    public void setUp() {
        trello = new TrelloImpl(TEST_APPLICATION_KEY, "", httpClient);
    }

    @Test
    public void testGetOrganization() {
        Organization organization = trello.getOrganization(ORG_ID);

        assertThat(organization).isNotNull();
        assertThat(organization.getId()).isEqualTo(ORG_ID);
    }

    @Test
    public void testGetOrganizationBoards() {
        List<Board> organizationBoards = trello.getOrganizationBoards(ORG_ID);

        assertThat(organizationBoards).isNotNull();
        assertThat(organizationBoards).hasSize(1);
        assertThat(organizationBoards.get(0).getId()).isEqualTo("518baad5b05dbf4703004852");
    }

    @Test
    public void testGetOrganizationMembers() {
        List<Member> organizationMembers = trello.getOrganizationMembers(ORG_ID);

        assertThat(organizationMembers).isNotNull();
        assertThat(organizationMembers).hasSize(1);
        assertThat(organizationMembers.get(0).getId()).isEqualTo("5187a69eabd0b7305100beaa");
    }
}
