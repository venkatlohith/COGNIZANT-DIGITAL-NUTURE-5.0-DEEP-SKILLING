import axios from 'axios';
import GitClient from './GitClient';

jest.mock('axios');

describe('Git Client Tests', () => {

  test('should return repository names for techiesyed', async () => {
    const mockRepos = [
      { name: 'appscentricsolutions' },
      { name: 'ArrayListDemo' },
      { name: 'GitHubDemoApp' }
    ];

    axios.get.mockResolvedValue({ data: mockRepos });

    const response = await GitClient.getRepositories('techiesyed');

    expect(axios.get).toHaveBeenCalledWith(
      'https://api.github.com/users/techiesyed/repos'
    );
    expect(response.data).toEqual(mockRepos);

    const repoNames = response.data.map((repo) => repo.name);
    expect(repoNames).toEqual([
      'appscentricsolutions',
      'ArrayListDemo',
      'GitHubDemoApp'
    ]);
  });

});
