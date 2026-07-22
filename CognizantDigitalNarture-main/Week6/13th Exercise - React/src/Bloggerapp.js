import React from 'react';
import { books } from './books';
import { courses } from './courses';
import { blogs } from './blogs';
import Notice from './Notice';

class Bloggerapp extends React.Component {
  render() {
    // Technique 1: element variable built with map() -- list rendering with keys
    const bookdet = (
      <ul>
        {books.map((book) => (
          <div key={book.id}>
            <h3> {book.bname}</h3>
            <h4>{book.price}</h4>
          </div>
        ))}
      </ul>
    );

    // Technique 2: the && (logical AND) operator for inline conditional rendering
    const content =
      blogs.length > 0 &&
      blogs.map((blog) => (
        <div key={blog.id}>
          <h3>{blog.title}</h3>
          <h4>{blog.author}</h4>
          <p>{blog.body}</p>
        </div>
      ));

    // Technique 3: the ternary operator for if/else style conditional rendering
    const coursedet =
      courses.length > 0 ? (
        <ul>
          {courses.map((course) => (
            <div key={course.id}>
              <h3>{course.cname}</h3>
              <h4>{course.date}</h4>
            </div>
          ))}
        </ul>
      ) : (
        <p>No courses available</p>
      );

    // Technique 4: an if statement that returns different JSX altogether
    if (books.length === 0 && blogs.length === 0 && courses.length === 0) {
      return <h2>Nothing to display right now.</h2>;
    }

    return (
      <div>
        {/* Technique 5: a component that prevents itself from rendering (returns null) */}
        <Notice message={null} />

        <div className="pagelayout">
          <div className="mystyle1">
            <h1> Course Details</h1>
            {coursedet}
          </div>
          <div className="st2">
            <h1> Book Details</h1>
            {bookdet}
          </div>
          <div className="v1">
            <h1> Blog Details</h1>
            {content}
          </div>
        </div>
      </div>
    );
  }
}

export default Bloggerapp;
