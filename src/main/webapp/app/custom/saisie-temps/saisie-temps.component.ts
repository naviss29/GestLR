import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-saisie-temps',
  templateUrl: './saisie-temps.component.html',
  styleUrls: ['saisie-temps.css']
})
export class SaisieTempsComponent implements OnInit {

    /* Initialisation des variables */
    commentaires: string;

    date1: Date;
    date2: Date;
    dates: Date[];
    rangeDates: Date[];
    minDate: Date;
    maxDate: Date;
    invalidDates: Array<Date>;

    ngOnInit() {

        const today = new Date();
        const month = today.getMonth();
        const year = today.getFullYear();
        const prevMonth = (month === 0) ? 11 : month - 1;
        const prevYear = (prevMonth === 11) ? year - 1 : year;
        const nextMonth = (month === 11) ? 0 : month + 1;
        const nextYear = (nextMonth === 0) ? year + 1 : year;
        this.minDate = new Date();
        this.minDate.setMonth(prevMonth);
        this.minDate.setFullYear(prevYear);
        this.maxDate = new Date();
        this.maxDate.setMonth(nextMonth);
        this.maxDate.setFullYear(nextYear);

        const invalidDate = new Date();
        invalidDate.setDate(today.getDate() - 1);
        this.invalidDates = [today, invalidDate];
    }

}
