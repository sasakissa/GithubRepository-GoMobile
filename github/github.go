package github

import (
	"io/ioutil"
	"net/http"
	"net/url"
)

type GithubService interface {
	SearchRepos(terms string) *RepositorySearchResult
}

type GithubClient struct {
	RequestUrl string
}

func DefaultClient() *GithubClient {
	return &GithubClient{searchURL}
}

func (client *GithubClient) SearchRepos(terms string) *RepositorySearchResult {

	q := url.QueryEscape(terms)
	resp, err := http.Get(client.RequestUrl + "?q=" + q)
	if err != nil {
		return &RepositorySearchResult{err.Error(), ErrorNetwork()}
	}

	if resp.StatusCode != http.StatusOK {
		resp.Body.Close()
		return &RepositorySearchResult{resp.Status, ErrorHttp()}
	}

	byteArray, _ := ioutil.ReadAll(resp.Body)
	resp.Body.Close()
	return &RepositorySearchResult{string(byteArray), Success()}
}

const searchURL = "https://api.github.com/search/repositories"

type RepositorySearchResult struct {
	Result string
	Status int
}

type StatusCode int

func Success() int {
	return 1
}

func ErrorNetwork() int {
	return -1
}

func ErrorHttp() int {
	return -2
}
