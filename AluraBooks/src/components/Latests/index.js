import { books } from './latestsData'
import styled from 'styled-components'
import { Title } from '../Title'
import RecomendationCard from '../RecomendationCard'
import book_img from '../../images/livro2.png'

const LatestsContainer = styled.section`
    background-color: #EBECEE;
    padding-bottom: 20px;
    display: flex;
    flex-direction: column;
`

const NewBooksContainer = styled.div`
    width: 100%;
    padding: 30px 0;
    background-color: #FFF;
    color: #EB9B00;
    font-size: 36px;
    text-align: center;
    margin: 0;
`

function Latests() {
    return (
        <LatestsContainer>
            <Title 
                color="#EB9B00" 
                fontSize="46px" 
            >Latests</Title>
            <NewBooksContainer> 
            { books.map( book => (
                <img src={book.src}/>
            ) ) } 
            </NewBooksContainer>
            <RecomendationCard
                title='perhaps you got interest in...'
                subtitle='Angular 11'
                desciption='building a google application'
                img={book_img}
            />

        </LatestsContainer>
    )
}

export default Latests