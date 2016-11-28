package com.julienvey.trello.unit;

import com.julienvey.trello.Trello;
import com.julienvey.trello.TrelloHttpClient;
import com.julienvey.trello.domain.Organization;
import com.julienvey.trello.impl.TrelloImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class OrganizationGetUnitTest {
    private Trello trello;
    private TrelloHttpClient httpClient;

    @Before
    public void setUp() {
        httpClient = Mockito.mock(TrelloHttpClient.class);
        trello = new TrelloImpl("", "", httpClient);
    }

    @Test
    public void testGetOrganization() {
        //Given
        Organization mockOrganization = new Organization();
        mockOrganization.setId("idOrg");

        when(httpClient.get(anyString(), any(Class.class), (String[]) anyVararg())).thenReturn(mockOrganization);

        //When
        Organization organization = trello.getOrganization("idOrg");

        //Then
        assertThat(mockOrganization).isNotNull();
        assertThat(mockOrganization.getId()).isEqualTo("idOrg");

        verify(httpClient).get(eq("https://api.trello.com/1/organizations/{organizationId}?key={applicationKey}&token={userToken}"),
                eq(Organization.class), eq("idOrg"), eq(""), eq(""));
        verifyNoMoreInteractions(httpClient);
    }
}
