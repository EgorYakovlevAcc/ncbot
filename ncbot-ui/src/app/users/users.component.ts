import { Component, OnInit } from '@angular/core';
import {UserServiceService} from "../user-service.service";
import {User} from "../user";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users:User[];
  constructor(private userService: UserServiceService) { }

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe((result:User[]) =>
      {
        this.users = result;
      }
    )
  }

}
