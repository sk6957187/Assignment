import React, { Component } from 'react';

class ViewPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      bioDataList: []
    };
  }

  async componentDidMount() {
    try {
      const response = await fetch('http://localhost:8080/view');
      const data = await response.json();
      this.setState({ bioDataList: data });
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

    extractDriveId(url) {
    const match = url.match(/[-\w]{25,}/);
    return match ? match[0] : '';
    };


  render() {
    const { bioDataList } = this.state;

    return (
      <div className="p-4">
        <h1 className="text-2xl font-bold mb-4">All BioData Entries</h1>

        {bioDataList.length === 0 ? (
          <p>No data available.</p>
        ) : (
          <table border="1" cellPadding="10">
            <thead>
              <tr>
                <th>ID</th><th>Name</th><th>Age</th><th>DOB</th>
                <th>Address</th><th>Image</th><th>Video</th><th>Audio</th><th>Text File</th>
                <th>Created Time</th><th>Updated Time</th>
              </tr>
            </thead>
            <tbody>
              {bioDataList.map((bd) => (
                <tr key={bd.id}>
                  <td>{bd.id}</td>
                  <td>{bd.name}</td>
                  <td>{bd.age}</td>
                  <td>{bd.dob}</td>
                  <td>{bd.address}</td>
                  <td>
                    <img
                        src={`https://drive.google.com/thumbnail?id=${this.extractDriveId(bd.image)}`}
                        alt="img"
                        width="100"
                    />
                </td>

                  <td>
                    <iframe
                      src={bd.video}
                      title={`video-${bd.id}`}
                      width="200"
                      height="150"
                      allow="autoplay"
                      controls
                    />
                  </td>
                  <td>
                    <audio controls src={bd.audio} />
                    {/* <audio
                      controls
                      src={`https://docs.google.com/uc?export=download&id=${this.extractDriveId(bd.audio)}`}
                      title={`audio-${bd.id}`}
                    /> */}

                  </td>
                  <td>
                    <a href={bd.textFile} target="_blank" rel="noreferrer">
                      View
                    </a>
                  </td>
                  <td>{bd.createdTime}</td>
                  <td>{bd.updateTime}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    );
  }
}

export default ViewPage;
