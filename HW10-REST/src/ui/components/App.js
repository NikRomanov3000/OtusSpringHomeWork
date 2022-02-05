import React from 'react'

const Header = (props) => (
    <h1>{props.title}</h1>
);

export default class App extends React.Component {

    constructor() {
        super();
        this.state = {authors: []};
    }

    componentDidMount() {
        fetch('/api/author')
            .then(response => response.json())
            .then(authors => this.setState({authors}));
    }

    render() {
        return (
            <React.Fragment>
                <Header title={'Authors'}/>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Comment</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.authors.map((author, i) => (
                            <tr key={i}>
                                <td>{author.id}</td>
                                <td>{author.name}</td>
                                <td>{author.comment}</td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </React.Fragment>
        )
    }
};
