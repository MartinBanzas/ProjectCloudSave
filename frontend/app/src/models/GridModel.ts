class GridModel {
    id: number;
    score: number;
    width: number;
    height: number;
    style: string;
    notes: string;
    mime: string;
    language: string;
    url: string;
    thumbnail: string;
    type: string;
    author: {};
  
    constructor(
      id: number,
      score: number,
      width: number,
      height: number,
      style: string,
      notes: string,
      mime: string,
      language: string,
      url: string,
      thumbnail: string,
      type: string,
      author: {}
    ) {
      this.id = id;
      this.score = score;
      this.width = width;
      this.height = height;
      this.style = style;
      this.notes = notes;
      this.mime = mime;
      this.language = language;
      this.url = url;
      this.thumbnail = thumbnail;
      this.type = type;
      this.author = author;
    }
  }

  export default GridModel